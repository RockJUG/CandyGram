package com.adamclmns.examples;

import com.adamclmns.data.CandyBar;
import com.adamclmns.data.CandyBarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The REST Endpoint for "/candybars" with GET, and POST handlers
 */
@RestController
@RequestMapping("/candyBars")
public class CandyBarREST {
    private final CandyBarRepository candyBarRepository;

    /**
     * Autowired Constructor let's SpringBoot manage the arguments for you.
     * <p>
     * see Spring Dependency Injection
     * - https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-spring-beans-and-dependency-injection.html
     *
     * @param repo the CandyBarRepository Instance
     */
    @Autowired
    public CandyBarREST(CandyBarRepository repo) {
        this.candyBarRepository = repo;
    }


    /**
     * Find all candy bars list.
     *
     * @return the list of all CandyBars
     */
    @GetMapping
    @ResponseBody
    public List<CandyBar> findAllCandyBars() {
        return this.candyBarRepository.findAll();
    }

    /**
     * Save a CandyBar.
     *
     * @param candyBar the candy bar
     * @return the candy bar
     */
    @PostMapping
    @ResponseBody
    public CandyBar saveCandyBar(@RequestBody CandyBar candyBar) {
        return this.candyBarRepository.save(candyBar);
    }

    /**
     * Find candy bar by id.
     *
     * @param id the id
     * @return the candy bar
     */
    @GetMapping(value = "/id/{id}")
    @ResponseBody
    public CandyBar findCandyBarById(@PathVariable("id") Long id) {
        return this.candyBarRepository.findById(id);
    }

    @GetMapping(value = "/name/{name}")
    @ResponseBody
    public List<CandyBar> findbyNameLike(@PathVariable("name") String name) {
        return this.candyBarRepository.findByNameIsLike(name);
    }

    @GetMapping(value = "/manufacturer/{manufacturer}")
    @ResponseBody
    public List<CandyBar> findByManufacturerLike(@PathVariable("manufacturer") String manufacturer) {
        return this.candyBarRepository.findAllByManufacturerIsLike(manufacturer);
    }

    @GetMapping(value = "/distribution/{distribution}")
    @ResponseBody
    public List<CandyBar> findByDistributionLike(@PathVariable("distribution") String distribution) {
        return this.candyBarRepository.findAllByDistributionContaining(distribution);
    }
}
