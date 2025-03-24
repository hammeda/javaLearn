package fr.javalearn.game.generic.controller;

import fr.javalearn.game.generic.services.GenericService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The type Generic controller.
 *
 * @param <D> the type parameter
 * @param <S> the type parameter
 */
@RequiredArgsConstructor
@Getter
public abstract class GenericController<
        D,
        S extends GenericService<D>
        > {
    /**
     * The Service.
     */
    protected final S service;

    /**
     * Find all page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @GetMapping
    public Page<D> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    @GetMapping("/{id}")
    public Optional<D> findById(@PathVariable long id) {
        return service.findById(id);
    }

    /**
     * Save or update d.
     *
     * @param dto the dto
     * @return the d
     */
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public D saveOrUpdate(@RequestBody D dto) {
        return service.saveOrUpdate(dto);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }
}
