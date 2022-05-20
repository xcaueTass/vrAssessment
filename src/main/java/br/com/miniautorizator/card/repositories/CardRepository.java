package br.com.miniautorizator.card.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.miniautorizator.card.entities.CardEntity;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

	@Query(value = "SELECT * FROM CARDS WHERE CARD_NUMBER = :card", nativeQuery = true)
	Optional<CardEntity> findCard(@Param("card") String card);

	@Query(value = "SELECT * FROM CARDS WHERE CARD_NUMBER = :card AND PASSWORD = :password", nativeQuery = true)
	Optional<CardEntity> findCardAndPassword(@Param("card") String card, @Param("password") String password);

}
