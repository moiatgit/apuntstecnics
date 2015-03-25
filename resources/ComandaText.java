/*
 * Classe que extreu la informació d'una comanda de d'entorn operatiu
 * tipus el de l'exercici dels Gats.
 *
 * L'entrada esperada tindrà una de les següents formes:
 *      «prefix» ESPAI «comanda»
 *      «comanda»
 *
 * La comanda ha de coincidir amb alguna de les comandes conegudes.
 *
 * Notes:
 *  - distingeix majúscules/minúscules i espais
 *  - no inclou totes les comandes.
 *  - el main() inclou un exemple d'ús.
 */
public class ComandaText {
    public enum ErrorComanda { SENSE_ESPECIFICAR, ENTRADA_CORRECTA, ENTRADA_NULL, COMANDA_DESCONEGUDA, COMANDA_NO_SEPARADA_PER_ESPAI }
    private static String[] comandes = { "com estàs?", "aixeca't" };
    private String prefix = null;
    private String comanda = null;
    private ErrorComanda estat = ErrorComanda.SENSE_ESPECIFICAR;

    public ComandaText(String entrada) {
        analitzaEntrada(entrada);
    }
    public boolean esCorrecte() {
        return estat.equals(ErrorComanda.ENTRADA_CORRECTA);
    }
    public String getPrefix() {
        return prefix;
    }
    public String getComanda() {
        return comanda;
    }
    public ErrorComanda getEstat() {
        return estat;
    }

    private void analitzaEntrada(String entrada) {
        if (entrada == null) {
            estat = ErrorComanda.ENTRADA_NULL;
            return;
        }
        String comanda = extreuComanda(entrada);
        if (comanda == null) {
            estat = ErrorComanda.COMANDA_DESCONEGUDA;
            return;
        }
        if (!separatPerEspai(entrada, comanda)) {
            estat = ErrorComanda.COMANDA_NO_SEPARADA_PER_ESPAI;
            return;
        }
        this.prefix = extreuPrefix(entrada, comanda);
        this.comanda = comanda;
        estat = ErrorComanda.ENTRADA_CORRECTA;
    }

    private String extreuComanda(String entrada) {
        String comanda = null;
        for (int i=0; i < comandes.length; i++) {
            String comandaActual = comandes[i];
            if (entrada.endsWith(comandaActual)) {
                comanda = comandaActual;
            }
        }
        return comanda;
    }
    private boolean separatPerEspai(String entrada, String comanda) {
        boolean resultat;
        if (entrada.length() > comanda.length() + 2) {  // prefix mínim d'un caràcter
            resultat = Character.isWhitespace(entrada.charAt(entrada.length() - comanda.length() - 1));
        } else {
            resultat = entrada.length() == comanda.length();
        }
        return resultat;
    }
    private String extreuPrefix(String entrada, String comanda) {
        if (entrada.length() <= comanda.length() + 2) {
            return "";
        }
        return entrada.substring(0, entrada.length() - comanda.length() - 1);
    }


    public static void main(String[] args) {
        String[] entrades = {
            "Renat dorm",                   // comanda desconeguda
            " aixeca't",                    // comanda amb espai i sense prefix
            "Renataixeca't",                // sense separació prefix-comanda
            "aixeca't",                     // comanda correcta sense prefix
            "Renat II com estàs?"};         // comanda correcta amb prefix

        for (String entrada: entrades) {
            System.out.println("Analitzant l'entrada: '" + entrada + "'");
            ComandaText ct = new ComandaText(entrada);
            if (ct.esCorrecte()) {
                String comanda = ct.getComanda();
                String nom = ct.getPrefix();
                System.out.println("\tDemana la comanda '" + comanda + "' a '" + nom +"'");
            } else {
                System.out.println("\tEntrada no vàlida: " + ct.getEstat());
            }
        }
    }
}
