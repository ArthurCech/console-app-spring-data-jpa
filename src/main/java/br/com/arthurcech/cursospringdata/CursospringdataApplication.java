package br.com.arthurcech.cursospringdata;

import br.com.arthurcech.cursospringdata.service.CrudAlunoService;
import br.com.arthurcech.cursospringdata.service.CrudDisciplinaService;
import br.com.arthurcech.cursospringdata.service.CrudProfessorService;
import br.com.arthurcech.cursospringdata.service.RelatorioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class CursospringdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursospringdataApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CrudProfessorService crudProfessorService,
                                        CrudDisciplinaService crudDisciplinaService,
                                        CrudAlunoService crudAlunoService,
                                        RelatorioService relatorioService) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            Boolean isTrue = true;
            while (isTrue) {
                System.out.println("----- MENU -----");
                System.out.println("0 - Sair");
                System.out.println("1 - Professor");
                System.out.println("2 - Disciplina");
                System.out.println("3 - Aluno");
                System.out.println("4 - Relatórios");
                int opcao = scanner.nextInt();
                switch (opcao) {
                    case 1 -> crudProfessorService.menu(scanner);
                    case 2 -> crudDisciplinaService.menu(scanner);
                    case 3 -> crudAlunoService.menu(scanner);
                    case 4 -> relatorioService.menu(scanner);
                    default -> isTrue = false;
                }
            }
        };
    }

}
