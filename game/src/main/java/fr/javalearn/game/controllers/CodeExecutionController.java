package fr.javalearn.game.controllers;

import fr.javalearn.game.entities.Exercice;
import fr.javalearn.game.entities.TestCase;
import fr.javalearn.game.repositories.ExerciceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.tools.*;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/code")
public class CodeExecutionController {

    @Autowired
    private ExerciceRepository exerciceRepository;

    @PostMapping("/execute-code/{exerciceId}")
    public ResponseEntity<Map<String, Object>> executeCode(@PathVariable Long exerciceId, @RequestBody Map<String, String> request) {
        String code = request.get("code");

        if (code == null || code.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Code is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            // Récupérer l'exercice avec les test cases
            Exercice exercice = exerciceRepository.findById(exerciceId)
                    .orElseThrow(() -> new Exception("Exercice not found"));

            // Extraction du nom de la classe publique (par exemple "Test" dans "public class Test")
            String className = extractClassName(code);

            // Créer un fichier temporaire contenant le code à exécuter avec le bon nom de fichier
            File tempFile = new File(System.getProperty("java.io.tmpdir"), className + ".java");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(code);
            }

            // Compiler le code avec javac (en appelant javac via ProcessBuilder)
            ProcessBuilder compilerProcess = new ProcessBuilder("javac", tempFile.getPath());
            Process compile = compilerProcess.start();

            // Capturer la sortie d'erreur du processus de compilation
            StringBuilder errorOutput = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(compile.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    errorOutput.append(line).append("\n");
                }
            }

            int compileResult = compile.waitFor();

            if (compileResult != 0) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "Compilation failed");
                errorResponse.put("details", errorOutput.toString());  // Ajouter les erreurs de compilation dans la réponse
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }

            // Exécuter le code compilé (en appelant java via ProcessBuilder)
            ProcessBuilder runProcess = new ProcessBuilder("java", "-cp", tempFile.getParent(), className);

            // Capturer la sortie du programme Java
            StringBuilder output = new StringBuilder();
            Process run = runProcess.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n"); // Capturer tout ce qui est affiché dans la sortie standard
                }
            }

            run.waitFor();

            // Comparer le résultat avec les test cases associés à l'exercice
            Map<String, String> testResults = validateCodeWithTestCases(output.toString(), exercice.getTestCases());

            // Répondre avec succès et inclure la sortie du programme Java et les résultats des tests
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Code exécuté avec succès");
            response.put("result", output.toString());  // Retourne le résultat capturé dans la sortie du programme Java
            response.put("testResults", testResults);   // Retourne les résultats des tests

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erreur lors de l'exécution du code");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    // Méthode pour comparer la sortie du code avec les test cases de l'exercice
    private Map<String, String> validateCodeWithTestCases(String output, List<TestCase> testCases) {
        Map<String, String> testResults = new HashMap<>();

        for (TestCase testCase : testCases) {
            String expectedOutput = testCase.getOutput();
            if (output.trim().equals(expectedOutput.trim())) {
                testResults.put(testCase.getExercice().getTitre(), "Test passed");
            } else {
                testResults.put(testCase.getExercice().getTitre(),
                        "Test failed: expected \"" + expectedOutput + "\", but got \"" + output.trim() + "\"");
            }
        }

        return testResults;
    }

    // Méthode pour extraire le nom de la classe publique du code source
    private String extractClassName(String code) {
        // Rechercher la déclaration de la classe publique
        String className = "UnnamedClass";  // Valeur par défaut au cas où

        String regex = "public\\s+class\\s+(\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) {
            className = matcher.group(1); // Extraire le nom de la classe
        }

        return className;
    }
}
