package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Employees;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Administrateur
 */
public class FenRechercherEmploye {

    private JFrame fenetre;

    private JPanel jpRechercher;
    private JPanel jpSaisie;

    private JTextField txtPrenom;
    private JTextField txtNom;

    private JLabel lblPrenom;
    private JLabel lblNom;

    private JButton btnRechercher;
    private JButton btnSaisirDepartement;
    private JButton btnSaisirEmploye;

    private JTable tableEmploye;
    private JTable tableau;

    private DefaultTableModel model;
    private JScrollPane scrollPane;

    // Constructeur  fenetre
    public FenRechercherEmploye() {

        prepareGUI();

    }//constructeur

    private void prepareGUI() {

        fenetre = new JFrame();

        //creation panel Rechercher
        jpRechercher = new JPanel();
        fenetre.add(jpRechercher, BorderLayout.NORTH);

        lblPrenom = new JLabel("Prénom: ");
        jpRechercher.add(lblPrenom);
        txtPrenom = new JTextField(10);
        jpRechercher.add(txtPrenom);
        lblNom = new JLabel("Nom: ");
        jpRechercher.add(lblNom);
        txtNom = new JTextField(10);
        jpRechercher.add(txtNom);
        btnRechercher = new JButton("Rechercher");
        jpRechercher.add(btnRechercher);

        //creation panel Saisie
        jpSaisie = new JPanel();
        fenetre.add(jpSaisie, BorderLayout.SOUTH);
        btnSaisirDepartement = new JButton("Saisir Departement");
        jpSaisie.add(btnSaisirDepartement);
        btnSaisirEmploye = new JButton("Saisir Employé");
        jpSaisie.add(btnSaisirEmploye);

        //creation model table
        model = new DefaultTableModel();
        tableEmploye = new JTable(model);
        scrollPane = new JScrollPane(tableEmploye);
        fenetre.add(scrollPane, BorderLayout.CENTER);

        //boutons Rechercher
        btnRechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!txtPrenom.getText().trim().equals("")) {
                    runQueryFirstName();
                } else if (!txtNom.getText().trim().equals("")) {
                    runQueryLastName();
                }

            }
        });

        //boutons Saisir Departement
        btnSaisirDepartement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new FenSaisirDepartement();

            }
        });

        //boutons Saisir Employe
        btnSaisirEmploye.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new FenSaisirEmploye();
            }
        });

        //settings fenetre
        fenetre.setTitle("Détails employés");
        fenetre.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fenetre.setSize(600, 400);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
    }
    
    //attributs requetes
    private static String query_first_name = "from Employees e where e.firstName like '";
    private static String query_last_name = "from Employees e where e.lastName like '";

    //creer la requete pour firstName
    private void runQueryFirstName() {
        executeHQLQuery(query_first_name + txtPrenom.getText() + "%'");
    }

    //creer la requete pour lastName
    private void runQueryLastName() {
        executeHQLQuery(query_last_name + txtNom.getText() + "%'");
    }

    //execution requete HQL
    private void executeHQLQuery(String query) {
        
        try {
            Session session = HibernateUtil.currentSession();
            session.beginTransaction();
            Query q = session.createQuery(query);
            List resultat = q.list();
            afficherResultat(resultat);//swing
            session.getTransaction().commit();

        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e, "Hibernate erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //methodes pour l'affichage dans la table 
    private void afficherResultat(List listing) {

        Vector<String> enteteTable = new Vector<String>();
        Vector donneesTable = new Vector();

        //initialiser les entetes avec le bon texte
        enteteTable.add("Prenom");
        enteteTable.add("Nom");
        enteteTable.add("Date Embouche");
        enteteTable.add("Salaire");

        //remplir la table une ligne à la fois
        for (Object o : listing) {

            Employees emp = (Employees) o;

            //mettre le contenu dans une ligne de la table
            Vector<Object> ligne = new Vector<Object>();
            ligne.add(emp.getFirstName());
            ligne.add(emp.getLastName());
            ligne.add(emp.getHireDate());
            ligne.add(emp.getSalary());

            //ajout la ligne dans une table
            donneesTable.add(ligne);
        }

        //afficher maintenant la table sur le frame
        tableEmploye.setModel(new DefaultTableModel(donneesTable, enteteTable));
    }//afficherResultat
    
    //methodes combobox

}
