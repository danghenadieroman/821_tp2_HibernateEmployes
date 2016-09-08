package vue;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Administrateur
 */
public class FenSaisirDepartement {

    JFrame fenetre;

    JPanel jpPrincipal;
    JPanel jpFormulaire;
    JPanel jpBoutons;

    JLabel lblDepartementId;
    JLabel lblNomDepartement;
    JLabel lblManagerId;
    JLabel lblLocationId;

    JTextField txtDepartementId;
    JTextField txtNomDepartement;
    JTextField txtManagerId;
    JTextField txtLocationId;

    JButton btnEnregistrer;
    JButton btnQuitter;

    public FenSaisirDepartement() {

        prepareGUI();

    }

    private void prepareGUI() {

        fenetre = new JFrame();

        lblDepartementId = new JLabel("Departement ID: ");
        lblNomDepartement = new JLabel("Nom departement: ");
        lblManagerId = new JLabel("Manager ID: ");
        lblLocationId = new JLabel("Location ID: ");

        txtDepartementId = new JTextField(10);
        txtNomDepartement = new JTextField(10);
        txtManagerId = new JTextField(10);
        txtLocationId = new JTextField(10);

        btnEnregistrer = new JButton("Enregistrer");
        btnQuitter = new JButton("Quitter");
        
        jpPrincipal = new JPanel();
        fenetre.add(jpPrincipal);
        
        jpFormulaire = new JPanel(new GridLayout(2, 4));
        

        //settings
        fenetre.setTitle("Saisir Departement");
        fenetre.setSize(400, 300);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        fenetre.setVisible(true);
    }

}
