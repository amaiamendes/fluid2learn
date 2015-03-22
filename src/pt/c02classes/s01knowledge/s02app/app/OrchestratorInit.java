package pt.c02classes.s01knowledge.s02app.app;

import java.util.Scanner;

public class OrchestratorInit {

    public static void main(String[] args)
    {

        Scanner s = new Scanner(System.in);

        System.out.println("Digite maze ou animals: ");
        String game = s.nextLine();

        if (game.equalsIgnoreCase("maze")) {

            OrchestratorInteractive.main(null);
        }
        else {

            Orchestrator.main(null);
        }
    }
}
