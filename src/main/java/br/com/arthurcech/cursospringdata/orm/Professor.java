package br.com.arthurcech.cursospringdata.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "professores")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String prontuario;

    // cascade = CascadeType.ALL
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private Set<Disciplina> disciplinas;

    @Deprecated
    public Professor() {
    }

    public Professor(String nome, String prontuario) {
        this.nome = nome;
        this.prontuario = prontuario;
    }

    @PreRemove
    public void atualizaDisciplinasOnRemove() {
        this.disciplinas.forEach(disciplina -> disciplina.setProfessor(null));
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", name='" + nome + '\'' +
                ", prontuario='" + prontuario + '\'' +
                '}';
    }

}
