package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.tools.DocumentationTool;
import model.Departments;
import model.Employees;
import model.Locations;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Administrateur
 */
public class FenSaisirDepartement {

    JFrame fenetre;

    JPanel jpPrincipal;
    JPanel jpNord;
    JPanel jpFormulaire;
    JPanel jpBoutons;

    JLabel lblTitre;

    JLabel lblDepartementId;
    JLabel lblNomDepartement;
    JLabel lblManagerId;
    JLabel lblLocationId;

    JTextField txtDepartementId;
    JTextField txtNomDepartement;
    JTextField txtManagerId;
    JTextField txtLocationId;

    JButton btnListeEmployees;
    JButton btnListeLocations;

    JButton btnEnregistrer;
    JButton btnQuitter;

    public FenSaisirDepartement() {

        prepareGUI();

    }

    private void prepareGUI() {

        fenetre = new JFrame();

        lblTitre = new JLabel("Nouveau departement");
        lblDepartementId = new JLabel("Departement ID: ");
        lblNomDepartement = new JLabel("Nom departement: ");
        lblManagerId = new JLabel("Manager ID: ");
        lblLocationId = new JLabel("Location ID: ");

        txtDepartementId = new JTextField(10);
        txtNomDepartement = new JTextField(10);
        txtManagerId = new JTextField(10);
        txtLocationId = new JTextField(10);

        btnListeEmployees = new JButton("Sélectionner");
        btnListeLocations = new JButton("Sélectionner");

        btnEnregistrer = new JButton("Enregistrer");
        btnQuitter = new JButton("Quitter");

        jpPrincipal = new JPanel(new BorderLayout(10, 10));
        jpPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        fenetre.add(jpPrincipal);

        //panel jpNord
        jpNord = new JPanel();
        jpPrincipal.add(jpNord, BorderLayout.NORTH);
        lblTitre.setForeground(Color.WHITE);
        lblTitre.setFont(new Font(null, Font.BOLD, 16));

        jpNord.add(lblTitre);
        jpNord.setBackground(Color.GRAY);

        //panel jpFormulaire
        jpFormulaire = new JPanel(new GridLayout(5, 3, 20, 10));

        jpPrincipal.add(jpFormulaire, BorderLayout.CENTER);

        jpFormulaire.add(lblDepartementId);
        lblDepartementId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtDepartementId);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblNomDepartement);
        lblNomDepartement.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtNomDepartement);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblManagerId);
        lblManagerId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtManagerId);
        jpFormulaire.add(btnListeEmployees);

        jpFormulaire.add(lblLocationId);
        lblLocationId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtLocationId);
        jpFormulaire.add(btnListeLocations);

        jpFormulaire.add(new JLabel());
        jpFormulaire.add(new JLabel());

        //panel jpBoutons
        jpBoutons = new JPanel();
        jpPrincipal.add(jpBoutons, BorderLayout.SOUTH);
        jpBoutons.add(btnEnregistrer);
        jpBoutons.add(btnQuitter);

        //bouton Enregistrer
        btnEnregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

//                if (!txtNomDepartement.getText().trim().equals("")) {
//                    Session session = HibernateUtil.currentSession();
//                    Transaction tx = session.beginTransaction();
//
//                    Employees employe = new Employees();
//                    Locations location = new Locations();
//                    Departments nouveauDepartement = new Departments();
//                    nouveauDepartement.setDepartmentName(txtNomDepartement.getText().trim());
//                    Query requete = session.createQuery("from Employees e where e.employeeId = " + txtManagerId.getText().trim());
//
//                    nouveauDepartement.setEmployees((txtManagerId.getText().trim().equals("") ? null : employe));
//                    nouveauDepartement.setLocations((txtLocationId.getText().trim().equals("") ? null : location));
//                    ajouterDepartement(nouveauDepartement);
//                } else {
//                    JOptionPane.showMessageDialog(null, "Tout les champs sont obligatoir (nom departement)",
//                            "Hibernate erreur", JOptionPane.ERROR_MESSAGE);
//                }
                listDepartements();
            }

        });

        //bouton Quitter
        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fenetre.setVisible(false);
            }
        });

        //settings
        fenetre.setTitle("Saisir Departement");
        fenetre.setSize(400, 300);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
    }

    private static String requete_EmployeeId = "from Employees e where e.employeeId = ";
    private static String requete_LocationId = "from Locations l where l.locationId = ";

    public void ajouterDepartement(Departments departement) {

    }

    public void listDepartements() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        List employees = session.createQuery("FROM Departments d").list();
        for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
            Departments dep = (Departments) iterator.next();
            System.out.println("Departement ID: " + dep.getDepartmentId());
            System.out.println("Nom departement: " + dep.getDepartmentName());
            System.out.println("Employees: " + dep.getEmployees());
            System.out.println("Location: " + dep.getLocations().getCountries().getCountryName());
        }
        tx.commit();

    }

}
