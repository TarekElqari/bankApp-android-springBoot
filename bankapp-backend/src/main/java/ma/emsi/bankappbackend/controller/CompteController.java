package ma.emsi.bankappbackend.controller;

import ma.emsi.bankappbackend.entities.Compte;
import ma.emsi.bankappbackend.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<Compte> getAllComptes() {
        return compteService.getAllComptes();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Compte compte = compteService.getCompteById(id);
        return ResponseEntity.ok(compte);
    }

    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public Compte createCompte(@RequestBody Compte compte) {
        return compteService.createCompte(compte);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte updatedCompte) {
        Compte compte = compteService.updateCompte(id, updatedCompte);
        return ResponseEntity.ok(compte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        compteService.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }
}
