package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.tools.DocumentationTool;
import model.Departments;
import model.Employees;
import model.Locations;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Administrateur
 */
public class FenSaisirEmploye {

    JFrame fenetre;

    JPanel jpPrincipal;
    JPanel jpNord;
    JPanel jpFormulaire;
    JPanel jpBoutons;

    JLabel lblTitre;

    JLabel lblEmployeeId;
    JLabel lblFirstName;
    JLabel lblLastName;
    JLabel lblEmail;
    JLabel lblHireDate;
    JLabel lblJobId;

    JTextField txtEmployeeId;
    JTextField txtFirstName;
    JTextField txtLastName;
    JTextField txtEmail;
    JTextField txtHireDate;
    JTextField txtJobId;

    JButton btnEnregistrer;
    JButton btnQuitter;

    JComboBox<String> comboBoxJob = new JComboBox<String>();

    public FenSaisirEmploye() {

        prepareGUI();

    }

    private void prepareGUI() {

        fenetre = new JFrame();

        lblTitre = new JLabel("Nouveau employé");
        lblEmployeeId = new JLabel("Employé ID:");
        lblFirstName = new JLabel("Prenom:");
        lblLastName = new JLabel("Nom:");
        lblEmail = new JLabel("Courriel:");
        lblHireDate = new JLabel("Date d'embouche:");
        lblJobId = new JLabel("Poste du travail:");

        txtEmployeeId = new JTextField(10);
        txtFirstName = new JTextField(10);
        txtLastName = new JTextField(10);
        txtEmail = new JTextField(10);
        txtHireDate = new JTextField(10);
        txtJobId = new JTextField(10);

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
        jpFormulaire = new JPanel(new GridLayout(7, 3, 20, 10));
        jpPrincipal.add(jpFormulaire, BorderLayout.CENTER);

        jpFormulaire.add(lblEmployeeId);
        lblEmployeeId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtEmployeeId);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblFirstName);
        lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtFirstName);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblLastName);
        lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtLastName);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblEmail);
        lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtEmail);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblHireDate);
        lblHireDate.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtHireDate);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblJobId);
        lblJobId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtJobId);
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
        fenetre.setTitle("Formulaire");
        fenetre.setSize(500, 500);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
    }

    public void ajouterDepartement() {
        Session session = null;
        Transaction transaction = null;

//        try {
//            session = HibernateUtil.sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            Departments departement = new Departments();
//            Employees employeeSelectionne = executeHQLQueryEmployee("from Employees e where e.employeeId = "
//                    + comboBoxJob.getSelectedItem().toString());
//            Locations locationSelectionne = executeHQLQueryLocation("from Locations l where l.locationId = "
//                    + comboBoxLocation.getSelectedItem().toString());
//            departement.setDepartmentName(txtDepartmentName.getText().trim());
//            departement.setEmployees(employeeSelectionne);
//            departement.setLocations(locationSelectionne);
//            session.save(departement);
//            txtDepartementId.setText(departement.getDepartmentId() + "");
//            transaction.commit();
//            JOptionPane.showMessageDialog(null, "Departemenet ajouté avec succes", "Modification", JOptionPane.INFORMATION_MESSAGE);
//
//        } catch (HibernateException e) {
//            JOptionPane.showMessageDialog(null, "Echec ajout departement", "Echec formulaire", JOptionPane.ERROR_MESSAGE);
//            transaction.rollback();
//        }
    }

    private void genererComboBoxManagerID() {
//        comboBoxJob.addItem("aucun");
        Session session = new HibernateUtil().sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Employees");
        List listeEmployees = query.list();
        for (Object object : listeEmployees) {
            Employees employee = (Employees) object;
            comboBoxJob.addItem(Integer.toString(employee.getEmployeeId()));
        }
        session.close();
    }

    //execution requete HQL
    private Employees executeHQLQueryEmployee(String query) {

        try {
            Session session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query q = session.createQuery(query);
            List resultat = q.list();
            Employees employee = (Employees) resultat.get(0);
            session.getTransaction().commit();
            return employee;

        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e, "executeHQLQueryEmployee()", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    //execution requete HQL
    private Locations executeHQLQueryLocation(String query) {

        try {
            Session session = HibernateUtil.sessionFactory.openSession();
            session.beginTransaction();
            Query q = session.createQuery(query);
            List resultat = q.list();
            Locations location = (Locations) resultat.get(0);
            session.getTransaction().commit();
            return location;
        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, e, "executeHQLQueryLocation()", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    //ComboBox Locations Listener
    ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            Locations location = executeHQLQueryLocation("from Locations l where l.locationId = " + itemEvent.getItem());
            lblHireDate.setText(location.getCity());
        }
    };

}
