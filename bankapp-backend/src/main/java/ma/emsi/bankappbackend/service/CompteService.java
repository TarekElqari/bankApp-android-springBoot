package ma.emsi.bankappbackend.service;



import jakarta.persistence.EntityNotFoundException;
import ma.emsi.bankappbackend.entities.Compte;
import ma.emsi.bankappbackend.exceptions.InvalidBalanceException;
import ma.emsi.bankappbackend.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    public Compte getCompteById(Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compte with ID " + id + " not found"));
    }

    public Compte createCompte(Compte compte) {
        if (compte.getSolde() < 0) {
            throw new InvalidBalanceException("Balance cannot be negative");
        }
        compte.setDateCreation(new Date());
        return compteRepository.save(compte);
    }

    public Compte updateCompte(Long id, Compte updatedCompte) {
        return compteRepository.findById(id).map(compte -> {
            if (updatedCompte.getSolde() < 0) {
                throw new InvalidBalanceException("Balance cannot be negative");
            }
            compte.setSolde(updatedCompte.getSolde());
            compte.setDateCreation(updatedCompte.getDateCreation());
            compte.setType(updatedCompte.getType());
            return compteRepository.save(compte);
        }).orElseThrow(() -> new EntityNotFoundException("Compte with ID " + id + " not found"));
    }

    public void deleteCompte(Long id) {
        if (!compteRepository.existsById(id)) {
            throw new EntityNotFoundException("Compte with ID " + id + " not found");
        }
        compteRepository.deleteById(id);
    }
}
