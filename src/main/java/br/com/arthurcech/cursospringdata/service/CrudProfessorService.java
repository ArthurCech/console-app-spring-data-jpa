package br.com.arthurcech.cursospringdata.service;

import br.com.arthurcech.cursospringdata.orm.Disciplina;
import br.com.arthurcech.cursospringdata.orm.Professor;
import br.com.arthurcech.cursospringdata.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudProfessorService {

    private final ProfessorRepository professorRepository;

    public CrudProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Transactional
    public void menu(Scanner scanner) {
        Boolean isTrue = true;

        while (isTrue) {
            System.out.println("\nAções:");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar todos");
            System.out.println("4 - Deletar");
            System.out.println("5 - Visualizar");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> cadastrar(scanner);
                case 2 -> atualizar(scanner);
                case 3 -> visualizar();
                case 4 -> deletar(scanner);
                case 5 -> visualizarProfessor(scanner);
                default -> isTrue = false;
            }
        }

        System.out.println();
    }

    private void cadastrar(Scanner scanner) {
        System.out.print("Digite o nome do professor: ");
        String nome = scanner.next();

        System.out.print("Digite o prontuario do professor: ");
        String prontuario = scanner.next();

        Professor professor = new Professor(nome, prontuario);

        professorRepository.save(professor);
        System.out.println("Professor salvo com sucesso");
    }

    private void atualizar(Scanner scanner) {
        System.out.print("Digite o ID do professor: ");
        Long id = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(id);

        if (optional.isPresent()) {
            System.out.print("Digite o nome do professor: ");
            String nome = scanner.next();

            System.out.print("Digite o prontuario do professor: ");
            String prontuario = scanner.next();

            Professor professor = optional.get();
            professor.setNome(nome);
            professor.setProntuario(prontuario);

            professorRepository.save(professor);
            System.out.println("Professor atualizado com sucesso");
        } else {
            System.out.println("O ID do professor informado (" + id + ") é inválido");
        }
    }

    private void visualizar() {
        Iterable<Professor> professores = professorRepository.findAll();
        professores.forEach(System.out::println);
        System.out.println();
    }

    private void deletar(Scanner scanner) {
        System.out.print("Digite o ID do professor: ");
        Long id = scanner.nextLong();
        professorRepository.deleteById(id);
        System.out.println("Professor deletado com sucesso");
    }

    @Transactional
    private void visualizarProfessor(Scanner scanner) {
        System.out.print("Digite o ID do professor: ");
        Long id = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(id);

        if (optional.isPresent()) {
            Professor professor = optional.get();

            System.out.println("- ID: " + professor.getId());
            System.out.println("- Nome: " + professor.getNome());
            System.out.println("- Prontuario: " + professor.getProntuario());
            System.out.println("- Disciplinas:");

            for (Disciplina disciplina : professor.getDisciplinas()) {
                System.out.println("\tId: " + disciplina.getId());
                System.out.println("\tNome: " + disciplina.getNome());
                System.out.println("\tSemestre: " + disciplina.getSemestre());
                System.out.println();
            }
        } else {
            System.out.println("O ID do professor informado (" + id + ") é inválido");
        }
    }

}
