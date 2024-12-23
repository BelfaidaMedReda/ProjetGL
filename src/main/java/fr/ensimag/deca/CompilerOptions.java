package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl50
 * @date 01/01/2025
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private int registersNumber = 16;
    private boolean parallel = false;
    private boolean check = true;
    private boolean verify = false;
    private boolean parse = false;
    private boolean existRegisters = false;
    private boolean printBanner = false;
    private List<File> sourceFiles = new ArrayList<File>();


    
    public void parseArgs(String[] args) throws CLIException {
        for (String arg : args) {
            switch (arg) {
                case "-b":
                    printBanner = true;
                    break;
                case "-p":
                    parse = true;
                    break;
                case "-v":
                    verify = true;
                    break;
                case "-n":
                    check = false;
                    break;
                case "-r":
                    existRegisters = true;
                    break;
                case "-d":
                    debug++;
                    break;
                case "-P":
                    parallel = true;
                    break;
                default:
                    if(arg.endsWith(".deca")) {
                        File file = new File(arg); // Crée un objet File
                        if (file.exists() && file.isFile()) {
                            sourceFiles.add(file); // Ajoute le fichier à la liste s'il existe
                            System.out.println("Fichier ajouté : " + file.getAbsolutePath());
                        } else {
                            System.out.println("Le fichier " + arg+ " n'existe pas ou n'est pas un fichier valide.");
                        }
                    } else if (existRegisters && Integer.parseInt(arg) >= 4 && Integer.parseInt(arg) <= 16) {
                        registersNumber = Integer.parseInt(arg);
                    }
            }
        }
        if(parse && verify) {
            throw new IllegalArgumentException("Les options '-p' et '-v' sont incompatibles.");
        }
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

        throw new UnsupportedOperationException("not yet implemented");
    }

    protected void displayUsage() {
        System.out.println("Utilisation de la commande decac :");
        System.out.println("  decac [-b] [-p | -v] [-n] [-r X] [-d]* [-P] <fichier.deca>");
        System.out.println("Options disponibles :");
        System.out.println("  -b       Affiche une bannière.");
        System.out.println("  -p       Arrête après l'étape de construction de l'arbre.");
        System.out.println("  -v       Arrête après l'étape de vérifications.");
        System.out.println("  -n       Supprime les tests spécifiques.");
        System.out.println("  -r X     Limite les registres disponibles (X entre 4 et 16).");
        System.out.println("  -d       Active les traces de debug.");
        System.out.println("  -P       Compilation parallèle.");
        System.out.println("Les options '-p' et '-v' sont incompatibles.");
    }
}
