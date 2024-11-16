package ma.emsi.bankappbackend.controller;



import ma.emsi.bankappbackend.entities.Compte;
import ma.emsi.bankappbackend.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        return compte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public Compte createCompte(@RequestBody Compte compte) {
        return compteRepository.save(compte);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte updatedCompte) {
        return compteRepository.findById(id).map(compte -> {
            compte.setSolde(updatedCompte.getSolde());
            compte.setDateCreation(updatedCompte.getDateCreation());
            compte.setType(updatedCompte.getType());
            return ResponseEntity.ok(compteRepository.save(compte));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

