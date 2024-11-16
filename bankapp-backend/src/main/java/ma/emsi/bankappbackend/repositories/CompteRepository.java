package ma.emsi.bankappbackend.repositories;

import ma.emsi.bankappbackend.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, Long> {
}
