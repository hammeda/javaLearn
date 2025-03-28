package fr.javalearn.game.controllers;

import fr.javalearn.game.entities.Exercice;
import fr.javalearn.game.entities.TestCase;
import fr.javalearn.game.repositories.ExerciceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
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
            errorResponse.put("message", "Le code est vide");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            Exercice exercice = exerciceRepository.findById(exerciceId)
                    .orElseThrow(() -> new Exception("Exercice not found"));

            String className = extractClassName(code);
            File tempFile = new File(System.getProperty("java.io.tmpdir"), className + ".java");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(code);
            }

            ProcessBuilder compilerProcess = new ProcessBuilder("javac", tempFile.getPath());
            Process compile = compilerProcess.start();

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
                errorResponse.put("details", errorOutput.toString());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }

            ProcessBuilder runProcess = new ProcessBuilder("java", "-cp", tempFile.getParent(), className);
            Process run = runProcess.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            StringBuilder errorExecutionOutput = new StringBuilder();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(run.getErrorStream()))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorExecutionOutput.append(line).append("\n");
                }
            }

            run.waitFor();

            if (!errorExecutionOutput.toString().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "Execution failed");
                errorResponse.put("errorDetails", errorExecutionOutput.toString());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }

            Map<String, String> testResults = validateCodeWithTestCases(output.toString(), exercice.getTestCases());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Code exécuté avec succès");
            response.put("result", output.toString());
            response.put("testResults", testResults);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erreur lors de l'exécution du code");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

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

    private String extractClassName(String code) {
        String className = "UnnamedClass";
        String regex = "public\\s+class\\s+(\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        if (matcher.find()) {
            className = matcher.group(1);
        }
        return className;
    }
}