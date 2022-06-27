package br.com.arthurcech.cursospringdata.service;

import br.com.arthurcech.cursospringdata.orm.Aluno;
import br.com.arthurcech.cursospringdata.orm.Disciplina;
import br.com.arthurcech.cursospringdata.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudAlunoService {

    private final AlunoRepository alunoRepository;

    public CrudAlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public void menu(Scanner scanner) {
        Boolean isTrue = true;

        while (isTrue) {
            System.out.println("----- ALUNO -----");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar todos");
            System.out.println("4 - Deletar");
            System.out.println("5 - Visualizar aluno");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> cadastrar(scanner);
                case 2 -> atualizar(scanner);
                case 3 -> visualizar();
                case 4 -> deletar(scanner);
                case 5 -> visualizarAluno(scanner);
                default -> isTrue = false;
            }
        }

        System.out.println();
    }

    private void cadastrar(Scanner scanner) {
        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.next();

        System.out.print("Digite a idade do aluno: ");
        Integer idade = scanner.nextInt();

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setIdade(idade);

        alunoRepository.save(aluno);
        System.out.println("Aluno salvo com sucesso");
    }

    private void atualizar(Scanner scanner) {
        System.out.print("Digite o ID do aluno: ");
        Long id = scanner.nextLong();

        Optional<Aluno> optional = alunoRepository.findById(id);

        if (optional.isPresent()) {
            Aluno aluno = optional.get();

            System.out.print("Digite o novo nome do aluno: ");
            String nome = scanner.next();

            System.out.print("Digite a nova idade do aluno: ");
            Integer idade = scanner.nextInt();

            aluno.setNome(nome);
            aluno.setIdade(idade);

            alunoRepository.save(aluno);
            System.out.println("Aluno atualizado com sucesso");
        } else {
            System.out.println("O ID do aluno informado (" + id + ") é inválido");
        }
    }

    private void visualizar() {
        Iterable<Aluno> alunos = alunoRepository.findAll();
        alunos.forEach(System.out::println);
        System.out.println();
    }

    private void deletar(Scanner scanner) {
        System.out.print("Digite o ID do aluno: ");
        Long id = scanner.nextLong();
        alunoRepository.deleteById(id);
        System.out.println("Aluno deletado com sucesso");
    }

    @Transactional
    private void visualizarAluno(Scanner scanner) {
        System.out.print("Digite o ID do aluno: ");
        Long id = scanner.nextLong();

        Optional<Aluno> optional = alunoRepository.findById(id);

        if (optional.isPresent()) {
            Aluno aluno = optional.get();

            System.out.println("- ID: " + aluno.getId());
            System.out.println("- Nome: " + aluno.getNome());
            System.out.println("- Idade: " + aluno.getIdade());
            System.out.println("- Disciplinas:");

            if (aluno.getDisciplinas() != null) {
                for (Disciplina disciplina : aluno.getDisciplinas()) {
                    System.out.println("\tDisciplina: " + disciplina.getNome());
                    System.out.println("\tSemestre: " + disciplina.getSemestre());
                    System.out.println();
                }
            }
        } else {
            System.out.println("O ID do aluno informado (" + id + ") é inválido");
        }
    }

}
