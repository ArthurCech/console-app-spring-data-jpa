package br.com.arthurcech.cursospringdata.repository;

import br.com.arthurcech.cursospringdata.orm.Aluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {

    List<Aluno> findByNome(String nome);

    List<Aluno> findByNomeContaining(String nome);

    List<Aluno> findByNomeStartingWith(String nome);

    List<Aluno> findByNomeStartingWithAndIdadeLessThanEqual(String nome, Integer idade);

    @Query("SELECT a FROM Aluno a WHERE a.nome LIKE :nome% AND a.idade >= :idade")
    List<Aluno> findNomeIdadeIgualOuMaior(String nome, Integer idade);

    @Query("SELECT a FROM Aluno a INNER JOIN a.disciplinas d WHERE a.nome LIKE :nomeAluno% AND a.idade >= :idadeAluno AND d.nome like :nomeDisciplina%")
    List<Aluno> findNomeIdadeIgualOuMaiorMatriculado(String nomeAluno, Integer idadeAluno, String nomeDisciplina);

}
