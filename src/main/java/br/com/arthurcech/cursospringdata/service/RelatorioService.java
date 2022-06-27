package br.com.arthurcech.cursospringdata.service;

import br.com.arthurcech.cursospringdata.orm.Aluno;
import br.com.arthurcech.cursospringdata.orm.Professor;
import br.com.arthurcech.cursospringdata.repository.AlunoRepository;
import br.com.arthurcech.cursospringdata.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    public RelatorioService(AlunoRepository alunoRepository,
                            ProfessorRepository professorRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }

    public void menu(Scanner scanner) {
        Boolean isTrue = true;

        while (isTrue) {
            System.out.println("\nRelatório:");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Alunos por um dado nome");
            System.out.println("2 - Alunos por um dado nome e idade menor ou igual");
            System.out.println("3 - Alunos por um dado nome e idade maior ou igual");
            System.out.println("4 - Alunos matriculados com um dado nome e idade maior ou igual");
            System.out.println("5 - Professores atribuídos");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> alunosPorNome(scanner);
                case 2 -> alunosPorNomeIdadeMenorOuIgual(scanner);
                case 3 -> alunosPorNomeIdadeMaiorOuIgual(scanner);
                case 4 -> alunosMatriculadosComNomeIdadeMaiorOuIgual(scanner);
                case 5 -> professoresAtribuidos(scanner);
                default -> isTrue = false;
            }
        }

        System.out.println();
    }

    private void alunosPorNome(Scanner scanner) {
        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.next();
        List<Aluno> alunos = this.alunoRepository.findByNomeStartingWith(nome);
        alunos.forEach(System.out::println);
    }

    private void alunosPorNomeIdadeMenorOuIgual(Scanner scanner) {
        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.next();

        System.out.print("Digite a idade do aluno: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findByNomeStartingWithAndIdadeLessThanEqual(nome, idade);
        alunos.forEach(System.out::println);
    }

    private void alunosPorNomeIdadeMaiorOuIgual(Scanner scanner) {
        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.next();

        System.out.print("Digite a idade do aluno: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaior(nome, idade);
        alunos.forEach(System.out::println);
    }

    private void alunosMatriculadosComNomeIdadeMaiorOuIgual(Scanner scanner) {
        System.out.print("Digite o nome do aluno: ");
        String nomeAluno = scanner.next();

        System.out.print("Digite a idade do aluno: ");
        Integer idadeAluno = scanner.nextInt();

        System.out.print("Digite o nome da disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaiorMatriculado(nomeAluno, idadeAluno, nomeDisciplina);
        alunos.forEach(System.out::println);
    }

    private void professoresAtribuidos(Scanner scanner) {
        System.out.print("Digite o nome do professor: ");
        String nomeProfessor = scanner.next();

        System.out.print("Digite o nome da disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Professor> professores = this.professorRepository.findProfessorAtribuido(nomeProfessor, nomeDisciplina);
        professores.forEach(System.out::println);
    }

}
