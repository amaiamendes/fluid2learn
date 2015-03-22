package pt.c01interfaces.s01knowledge.s02app.actors;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;
import java.util.Vector;
import java.util.Collections;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj1, obj2;
	
	public Enquirer()
	{

	}

	
	@Override
	public void connect(IResponder responder)
	{
        int i, j, k, flag1, flag2, flag3;
        String pergunta, respostaEsperada, resposta, teste1, teste2;
        IBaseConhecimento bc = new BaseConhecimento();
        IDeclaracao decl1, decl2;
        String animalsOriginal[] = bc.listaNomes();
        Vector <String> animals = new Vector<String>();
        Collections.addAll(animals, animalsOriginal);

        Vector <String> perguntasFeitas = new Vector <String>();


        for (i = 0; i < animals.size(); i++) // loop sobre a lista de animais
        {

            obj1 = bc.recuperaObjeto(animals.get(i)); // pega um animal na lista de animais
            decl1 = obj1.primeira(); // pega a primeira pergunta e resposta sobre esse animal

            flag1 = 1; // flag que indica se a resposta referente ao animal do Responder é igual a do animal que estamos chutando para uma dada pergunta
            flag2 = 0; // flag que indica se a resposta referente ao animal do Responder é oposta a do animal que estamos chutando para uma dada pergunta

            while (decl1 != null && flag1 != 0 && animals.size() > 1) // loop sobre a lista de perguntas e respostas dos animais
            {
                pergunta = decl1.getPropriedade(); // pega uma pergunta sobre o animal
                respostaEsperada = decl1.getValor(); // pega a resposta correspondente para esse animal

                flag3 = 0; // flag que indica se a pergunta já foi feita antes

                for (k = 0; k < perguntasFeitas.size(); k++) // loop sobre o vetor de perguntas ja feitas
                    if (pergunta.equalsIgnoreCase(perguntasFeitas.get(k)))
                        flag3 = 1;

                if (flag3 == 1) // se a pergunta já foi feita, pula-se para a proxima
                {
                    decl1 = obj1.proxima();
                    continue;
                }

                perguntasFeitas.addElement(pergunta); // adiciona a pergunta ao vetor de perguntas já feitas

                resposta = responder.ask(pergunta); // procura a resposta referente ao animal do Responder para uma dada pergunta
                if (resposta.equalsIgnoreCase(respostaEsperada))
                    flag1 = 1;
                else
                {
                    if (resposta.equalsIgnoreCase("nao") || resposta.equalsIgnoreCase("sim"))
                        flag2 = 1;

                    flag1 = 0;
                }

                for (j = i+1; j < animals.size();) // loop sobre o resto da lista de animais para eliminar elementos
                {

                    obj2 = bc.recuperaObjeto(animals.get(j)); // pega um animal do resto da lista de animais
                    decl2 = obj2.primeira(); // pega a primeira pergunta e resposta referente a esse animal

                    flag3 = 0; // flag que indica se a pergunta e a resposta são iguais a do animal no enquirer ou se a resposta é oposta

                    while (decl2 != null) // loop sobre as perguntas e respostas do animal
                    {
                        teste1 = decl2.getPropriedade();
                        teste2 = decl2.getValor();

                        if (teste1.equalsIgnoreCase(pergunta))
                        {
                            if (teste2.equalsIgnoreCase(respostaEsperada))
                                flag3 = 1;
                            else
                                flag3 = 2;
                            break;
                        }
                        else
                            decl2 = obj2.proxima();
                    }

                    /*pergunta e resposta do animal no enquirer conferem com o no responder e
                    * ou a pergunta não é encontrada no animal aqui
                    * ou é encontrada, mas a resposta é oposta
                    * */
                    if ((flag1 == 1 && (decl2 == null || flag3 == 2)))
                        animals.removeElementAt(j);

                    /*resposta do animal no enquirer não confere com o no responder e
                    * resposta do responder à pergunta é não sei e
                    * a pergunta é encontrada no animal aqui
                    * */
                    else if (flag1 == 0 && (flag2 == 0 && decl2 != null))
                        animals.removeElementAt(j);

                    /*resposta do animal no enquirer não confere com o no responder e
                    * resposta do responder é oposta à esperada pelo enquirer e
                    * ou a pergunta não foi encontrada no animal aqui,
                    * ou a pergunta foi encontrada, mas também é oposta à do animal no responder
                    * */
                    else if (flag1 == 0 && (flag2 == 1 && (decl2 == null || flag3 == 1)))
                        animals.removeElementAt(j);

                    else
                        j++;
                }
                decl1 = obj1.proxima();

                if (flag1 == 0)
                {
                    animals.removeElementAt(i);
                    i--;
                }
            }
            if (animals.size() == 1)
            {
                i = 0;
                break;
            }
        }

		boolean acertei = responder.finalAnswer(animals.get(i));
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
