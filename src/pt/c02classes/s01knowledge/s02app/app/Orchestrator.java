package pt.c02classes.s01knowledge.s02app.app;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;

import java.util.Vector;
import java.util.Scanner;

public class Orchestrator
{
	public static void main(String[] args)
	{
		IEnquirer enq;
		IResponder resp;
		IStatistics stat;
		
		IBaseConhecimento base = new BaseConhecimento();

        Scanner s = new Scanner (System.in);

        System.out.println("Digite o nome do animal desejado: ");
        String option = s.nextLine();

		base.setScenario("animals");

		Vector <String> listaAnimais = new Vector <String>();

        String[] a = base.listaNomes();

        for(int i = 0; i < a.length; i++) {

            listaAnimais.add(a[i]);
        }

        for (int animal = 0; animal < listaAnimais.size(); animal++) {

            if (listaAnimais.get(animal).equalsIgnoreCase(option)) {

                System.out.println("Enquirer com " + listaAnimais.get(animal) + "...");
                stat = new Statistics();
                resp = new ResponderAnimals(stat, listaAnimais.get(animal));
                enq = (IEnquirer) new EnquirerAnimals();
                enq.connect(resp);
                enq.discover();
                System.out.println("----------------------------------------------------------------------------------------\n");

                break;
            }
        }

	}
}