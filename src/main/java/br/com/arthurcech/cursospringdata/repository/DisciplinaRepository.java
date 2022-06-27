package br.com.arthurcech.cursospringdata.repository;

import br.com.arthurcech.cursospringdata.orm.Disciplina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {
}
