package br.com.arthurcech.cursospringdata.repository;

import br.com.arthurcech.cursospringdata.orm.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM professores p INNER JOIN disciplinas d ON p.id = d.professor_id" +
                    " WHERE p.nome like :nomeProfessor%" +
                    " AND d.nome LIKE :nomeDisciplina%")
    List<Professor> findProfessorAtribuido(String nomeProfessor, String nomeDisciplina);

}
