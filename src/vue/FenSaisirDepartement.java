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
 * @author Dan-Ghenadie Roman - 1395945
 * @ ver 2.0
 * @ date 09/09/2016
 * @ tp2 - 821 - groupe 212
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
    JLabel lblCity;

    JTextField txtDepartementId;
    JTextField txtDepartmentName;
    JTextField txtManagerId;
    JTextField txtLocationId;

    JButton btnEnregistrer;
    JButton btnQuitter;

    JComboBox<String> comboBoxManager = new JComboBox<String>();
    JComboBox<String> comboBoxLocation = new JComboBox<String>();

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
        lblCity = new JLabel();

        txtDepartementId = new JTextField(10);
        txtDepartmentName = new JTextField(10);
        txtManagerId = new JTextField(10);
        txtLocationId = new JTextField(10);

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
        txtDepartementId.setEditable(false);
        jpFormulaire.add(new JLabel());

        jpFormulaire.add(lblNomDepartement);
        lblNomDepartement.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(txtDepartmentName);
        jpFormulaire.add(new JLabel());

        // == COMBOBOX == Manager ID
        jpFormulaire.add(lblManagerId);
        lblManagerId.setHorizontalAlignment(SwingConstants.RIGHT);
        jpFormulaire.add(comboBoxManager);
        genererComboBoxManagerID();
        jpFormulaire.add(new JLabel());

        // == COMBOBOX == Location ID
        jpFormulaire.add(lblLocationId);
        lblLocationId.setHorizontalAlignment(SwingConstants.RIGHT);
        genererComboBoxLocationsID();
        jpFormulaire.add(comboBoxLocation);
        comboBoxLocation.addItemListener(itemListener);
        jpFormulaire.add(lblCity);//////

        //panel jpBoutons
        jpBoutons = new JPanel();
        jpPrincipal.add(jpBoutons, BorderLayout.SOUTH);
        jpBoutons.add(btnEnregistrer);
        jpBoutons.add(btnQuitter);

        //bouton Enregistrer
        btnEnregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!txtDepartmentName.getText().trim().equals("")) {
                    ajouterDepartement();
                } else {
                    JOptionPane.showMessageDialog(null, "Le champ departement est obligatoir",
                            "Formulaire invalide", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //bouton Quitter
        btnQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fenetre.setVisible(false);
            }
        });

        //fenetre configuration
        fenetre.setTitle("Formulaire");
        fenetre.setSize(500, 300);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
    }

    public void ajouterDepartement() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Departments departement = new Departments();
            Employees employeeSelectionne = executeHQLQueryEmployee("from Employees e where e.employeeId = "
                    + comboBoxManager.getSelectedItem().toString());
            Locations locationSelectionne = executeHQLQueryLocation("from Locations l where l.locationId = "
                    + comboBoxLocation.getSelectedItem().toString());
            departement.setDepartmentName(txtDepartmentName.getText().trim());
            departement.setEmployees(employeeSelectionne);
            departement.setLocations(locationSelectionne);
            session.save(departement);
            txtDepartementId.setText(departement.getDepartmentId() + "");
            transaction.commit();
            JOptionPane.showMessageDialog(null, "Departemenet ajouté avec succes",
                    "Modification", JOptionPane.INFORMATION_MESSAGE);
            fenetre.setVisible(false);

        } catch (HibernateException e) {
            JOptionPane.showMessageDialog(null, "Echec ajout departement",
                    "Echec formulaire", JOptionPane.ERROR_MESSAGE);
            transaction.rollback();
        }
    }

    private void genererComboBoxManagerID() {
//        comboBoxManager.addItem("aucun");
        Session session = new HibernateUtil().sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Employees");
        List listeEmployees = query.list();
        for (Object object : listeEmployees) {
            Employees employee = (Employees) object;
            comboBoxManager.addItem(Integer.toString(employee.getEmployeeId()));
        }
        session.close();
    }

    private void genererComboBoxLocationsID() {
//        comboBoxLocation.addItem(null);
        Session session = new HibernateUtil().sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Locations");
        List listeLocation = query.list();
        for (Object object : listeLocation) {
            Locations locations = (Locations) object;
            comboBoxLocation.addItem(Integer.toString(locations.getLocationId()));
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
            JOptionPane.showMessageDialog(null, e, "executeHQLQueryEmployee()",
                    JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, e, "executeHQLQueryLocation()",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    //ComboBox Locations Listener
    ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            Locations location = executeHQLQueryLocation("from Locations l where l.locationId = "
                    + itemEvent.getItem());
            lblCity.setText(location.getCity());
        }
    };

}
