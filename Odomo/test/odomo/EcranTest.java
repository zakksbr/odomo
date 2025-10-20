package odomo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * Tests portant sur la classe Odomo.
 */
public class EcranTest {

    @Test
    public void testEcranInchange() {
        String ecran_orig
                = "+----------------+-------------------------------------------------------------+\n"
                + "|HH:mm    JJ/MM  | Mode : MODEMODEMODE                                         |\n"
                + "|                |                                                             |\n"
                + "|intérieur :     |                                                             |\n"
                + "| ttttt°C        |                                                             |\n"
                + "|    yy% hygro   |                                                             |\n"
                + "|                |       TITRE_HISTO_TITRE_HISTO_TITRE_HISTO_TITRE_HISTO  LVER |\n"
                + "|extérieur :     |        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 LLL0 |\n"
                + "| TTTTT° C       |        1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 LLL1 |\n"
                + "|    YY% hygro   |        2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 LLL2 |\n"
                + "|  PPPP  hPa     |        3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 LLL3 |\n"
                + "|  SSSS  W/m2 SOL|        4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 LLL4 |\n"
                + "|                |        5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 LLL5 |\n"
                + "|      o   o     |        6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 LLL6 |\n"
                + "|   o   NNN   o  |        7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 LLL7 |\n"
                + "|  o NOO   NEE o |  LHOR LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL\\LLL8 |\n"
                + "|  oOOO VVV EEEo |                                                             |\n"
                + "|  o SOO   SEE o |                                                             |\n"
                + "|   o   SSS   o  |                                                             |\n"
                + "|      o   o     |                                                             |\n"
                + "+----------------+-------------------------------------------------------------+";
        assertEquals(ecran_orig, Ecran.ECRAN_VIDE);
    }

    @Test
    public void testMotifLigneHisto() {
        assertEquals(" 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2",
                Ecran.motifLigneHisto(2));
    }

    @Test
    public void testHistoLegendeAbscisse() {
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        assertEquals(" -22 -20 -18 -16 -14 -12 -10  -8  -6  -4  -2   0",
                Ecran.histoLegendeAbscisse());
        Ecran.typeHisto = Ecran.TYPE_HISTO_CHAUFFAGE;
        assertEquals(" 0   2   4   6   8  10  12  14  16  18  20  22  ",
                Ecran.histoLegendeAbscisse());
    }

    @Test
    public void testTitreHisto() {
        assertNotEquals("", Ecran.titreHisto());
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        assertEquals("Pluviométrie des 24 dernières heures :",
                Ecran.titreHisto());
    }

    @Test
    public void testHistoUniteAbscisse() {
        assertEquals("h", Ecran.histoUniteAbscisse());
    }

    @Test
    public void testHistoUniteOrdonnee() {
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        assertEquals("mm", Ecran.histoUniteOrdonnee());
    }

    @Test
    public void testHistoLegendeOrdonnee() {
        Meteo.initialiserDonneesParHeure();
        Meteo.initialiserDonneesParMinute();
        // pour la pluviométrie par heure
        double maxi = Meteo.pluvioHeure[0];
        for (int i = 1; i < 24; i++) {
            if (Meteo.pluvioHeure[i] > maxi) {
                maxi = Meteo.pluvioHeure[i];
            }
        }
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        assertEquals("" + (int) maxi, Ecran.histoLegendeOrdonnee(0));
        assertEquals("", Ecran.histoLegendeOrdonnee(1));
        assertEquals("", Ecran.histoLegendeOrdonnee(2));
        assertEquals("", Ecran.histoLegendeOrdonnee(3));
        assertEquals("" + (int) (maxi / 2), Ecran.histoLegendeOrdonnee(4));
        assertEquals("", Ecran.histoLegendeOrdonnee(5));
        assertEquals("", Ecran.histoLegendeOrdonnee(6));
        assertEquals("", Ecran.histoLegendeOrdonnee(7));
        assertEquals("0", Ecran.histoLegendeOrdonnee(8));
    }

    @Test
    public void testHistoLigneExempleDuSujet() {
        double[] pluvio = {2.3, 3.9, 1.0, 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 4.5, 1.7};
        Meteo.pluvioHeure = pluvio;
        StringBuilder ecran = new StringBuilder(Ecran.ECRAN_VIDE);
        Ecran.insererDonneesHistogramme(ecran);
        System.out.println(ecran);
    }

    @Test
    public void testHistoLigneExempleVide() {
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        double[] pluvio = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = pluvio;
        StringBuilder ecran = new StringBuilder(Ecran.ECRAN_VIDE);
        Ecran.insererDonneesHistogramme(ecran);
        System.out.println(ecran);
    }

    @Test
    public void testNumLigne() {
        double valMin = -15.4;
        double valMax = 17.3;
        // valeur max
        assertEquals(0, Ecran.numLigne(valMax, valMin, valMax));
        // valeur min
        assertEquals(7, Ecran.numLigne(valMin, valMin, valMax));
        // ligne au-dessus du min
        double pas = (valMax - valMin) / 7.;
        assertEquals(6, Ecran.numLigne(valMin + pas, valMin, valMax));
        assertEquals(7, Ecran.numLigne(valMin + .49 * pas, valMin, valMax));
        assertEquals(6, Ecran.numLigne(valMin + .51 * pas, valMin, valMax));
    }
}
