package br.com.arthurcech.cursospringdata.service;

import br.com.arthurcech.cursospringdata.orm.Aluno;
import br.com.arthurcech.cursospringdata.orm.Disciplina;
import br.com.arthurcech.cursospringdata.orm.Professor;
import br.com.arthurcech.cursospringdata.repository.AlunoRepository;
import br.com.arthurcech.cursospringdata.repository.DisciplinaRepository;
import br.com.arthurcech.cursospringdata.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Service
public class CrudDisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;

    public CrudDisciplinaService(DisciplinaRepository disciplinaRepository,
                                 ProfessorRepository professorRepository,
                                 AlunoRepository alunoRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
    }

    public void menu(Scanner scanner) {
        Boolean isTrue = true;

        while (isTrue) {
            System.out.println("----- DISCIPLINA -----");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar todos");
            System.out.println("4 - Deletar");
            System.out.println("5 - Matricular aluno");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> cadastrar(scanner);
                case 2 -> atualizar(scanner);
                case 3 -> visualizar();
                case 4 -> deletar(scanner);
                case 5 -> matricularAlunos(scanner);
                default -> isTrue = false;
            }
        }

        System.out.println();
    }

    private void cadastrar(Scanner scanner) {
        System.out.print("Digite o nome do disciplina: ");
        String nome = scanner.next();

        System.out.print("Digite o semestre do disciplina: ");
        Integer semestre = scanner.nextInt();

        System.out.print("Digite o ID do professor: ");
        Long professorId = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(professorId);

        if (optional.isPresent()) {
            Professor professor = optional.get();

            Set<Aluno> alunos = matricular(scanner);

            Disciplina disciplina = new Disciplina(nome, semestre, professor);
            disciplina.setAlunos(alunos);

            disciplinaRepository.save(disciplina);
            System.out.println("Disciplina salva com sucesso");
        }
    }

    private void atualizar(Scanner scanner) {
        System.out.print("Digite o ID da Disciplina a ser atualizada: ");
        Long id = scanner.nextLong();

        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(id);

        if (optionalDisciplina.isPresent()) {
            Disciplina disciplina = optionalDisciplina.get();

            System.out.print("Digite o nome do disciplina: ");
            String nome = scanner.next();

            System.out.print("Digite o semestre do disciplina: ");
            Integer semestre = scanner.nextInt();

            System.out.print("Digite o ID do professor: ");
            Long professorId = scanner.nextLong();

            Optional<Professor> optionalProfessor = professorRepository.findById(professorId);

            if (optionalProfessor.isPresent()) {
                Professor professor = optionalProfessor.get();

                Set<Aluno> alunos = matricular(scanner);

                disciplina.setNome(nome);
                disciplina.setSemestre(semestre);
                disciplina.setProfessor(professor);
                disciplina.setAlunos(alunos);

                disciplinaRepository.save(disciplina);
                System.out.println("Disciplina atualizada com sucesso");
            } else {
                System.out.println("O ID do professor informado (" + professorId + ") é inválido");
            }
        } else {
            System.out.println("O ID da disciplina informada (" + id + ") é inválida");

        }
    }

    private void visualizar() {
        Iterable<Disciplina> disciplinas = disciplinaRepository.findAll();
        disciplinas.forEach(System.out::println);
        System.out.println();
    }

    private void deletar(Scanner scanner) {
        System.out.print("Digite o ID da Disciplina: ");
        Long id = scanner.nextLong();
        disciplinaRepository.deleteById(id);
        System.out.println("Disciplina deletada com sucesso");
    }

    private void matricularAlunos(Scanner scanner) {
        System.out.print("Digite o ID da disciplina: ");
        Long id = scanner.nextLong();

        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(id);

        if (optionalDisciplina.isPresent()) {
            Disciplina disciplina = optionalDisciplina.get();

            Set<Aluno> novosAlunos = matricular(scanner);
            disciplina.getAlunos().addAll(novosAlunos);

            disciplinaRepository.save(disciplina);
        } else {
            System.out.println("O ID da disciplina informada (" + id + ") é inválida");
        }
    }

    private Set<Aluno> matricular(Scanner scanner) {
        Boolean isTrue = true;

        Set<Aluno> alunos = new HashSet<>();

        while (isTrue) {
            System.out.print("Digite o ID do aluno a ser matriculado (0 para sair): ");
            Long alunoId = scanner.nextLong();

            if (alunoId > 0) {
                System.out.println("ID do aluno: " + alunoId);
                Optional<Aluno> optional = alunoRepository.findById(alunoId);

                if (optional.isPresent()) {
                    alunos.add(optional.get());
                } else {
                    System.out.println("O ID do aluno informado (" + alunoId + ") é inválido");
                }
            } else {
                isTrue = false;
            }
        }

        return alunos;
    }

}
