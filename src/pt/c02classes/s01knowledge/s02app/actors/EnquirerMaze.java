package pt.c02classes.s01knowledge.s02app.actors;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

    IResponder responder;

    int x = 0, y = 0;

    Map<String, Integer> maze = new HashMap <String, Integer>();

    public void connect(IResponder responder) {
        this.responder = responder;
    }

    public boolean discover() {

        solve();

        if (responder.finalAnswer("cheguei")) {

            System.out.println("Você encontrou a saida!");

        } else {

            System.out.println("Fuém fuém fuém!");
        }

        return true;
    }

    private boolean solve() {

        int i;

        if (responder.ask("aqui").equalsIgnoreCase("saida")) {

            return true;
        }

        for (i = 0; i < 4; i++) {

            if (!maze.containsKey(x + ";" + y)) {

                maze.put(x + ";" + y, 1);
            }

            if (i == 0) {

                if ((responder.ask("norte").equalsIgnoreCase("passagem") || responder.ask("norte").equalsIgnoreCase("saida"))
                        && !maze.containsKey(x + ";" + (y + 1))) {

                    responder.move("norte");

                    y++;

                    if (solve()) {

                        return true;
                    }
                    else {

                        responder.move("sul");
                    }
                }
            }
            else if (i == 1) {

                if ((responder.ask("leste").equalsIgnoreCase("passagem") || responder.ask("leste").equalsIgnoreCase("saida")) && !maze.containsKey((x + 1) + ";" + y)) {

                    responder.move("leste");

                    x++;

                    if (solve()) {

                        return true;
                    }
                    else {

                        responder.move("oeste");
                    }
                }
            }
            else if (i == 2) {

                if ((responder.ask("sul").equalsIgnoreCase("passagem") || responder.ask("sul").equalsIgnoreCase("saida")) && !maze.containsKey(x + ";" + (y - 1))) {

                    responder.move("sul");

                    y--;

                    if (solve()) {

                        return true;
                    }
                    else {

                        responder.move("norte");
                    }
                }
            }
            else {

                if ((responder.ask("oeste").equalsIgnoreCase("passagem") || responder.ask("oeste").equalsIgnoreCase("saida")) && !maze.containsKey((x - 1) + ";" + y)) {

                    responder.move("oeste");

                    x--;

                    if (solve()) {

                        return true;
                    }
                    else {

                        responder.move("leste");
                    }
                }
            }
        }

        return false;
    }
}
