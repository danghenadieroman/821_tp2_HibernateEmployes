package vue;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class FenListeEmployees {

    private JFrame fenetre;

    private JTable tableEmploye;
    private JTable tableau;

    private DefaultTableModel model;
    private JScrollPane scrollPane;

    private static String query_Employees = "from Employees";

    public FenListeEmployees() {
        prepareGUI();
    }

    private void prepareGUI() {
        fenetre = new JFrame();

        //creation model table
        model = new DefaultTableModel();
        tableEmploye = new JTable(model);
        scrollPane = new JScrollPane(tableEmploye);
        fenetre.add(scrollPane, BorderLayout.CENTER);

        executeHQLQuery(query_Employees);
        //settings fenetre
        fenetre.setTitle("Détails employés");
        fenetre.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fenetre.setSize(600, 400);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
    }

    //execution requete HQL
    public void executeHQLQuery(String query) {

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

}
