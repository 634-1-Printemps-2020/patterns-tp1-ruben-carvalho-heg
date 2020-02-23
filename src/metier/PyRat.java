package metier;

import java.util.*;

public class PyRat {
    Set<Point> f;
    Map<Point, Set<Point>> mapLaby;
    List<Point> chemin;
    List<Point> pIna;

    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        this.f = new HashSet<Point>(fromages);

        this.mapLaby = new HashMap<>();
        for(Point cle : laby.keySet()) {
            Set<Point> voisin = new HashSet<Point>(laby.get(cle));
            this.mapLaby.put(cle, voisin);
        }

        this.chemin = new ArrayList<>();
        this.pIna = new ArrayList<Point>();
    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */
    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(2,1);
        Point pt2 = new Point(3,1);
        Point pt3 = new Point(6,0);
        System.out.println((fromageIci(pt1, fromages) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt2);
        System.out.println((passagePossible(pt1, pt3, laby) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt3);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2, mapLaby) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(position, laby));
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos, List<Point> fromages) {
        return fromages.contains(pos);
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        return f.contains(pos);
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Point de, Point a, Map<Point, List<Point>> laby) {
        return laby.get(de).contains(a);
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant(Point de, Point a, Map<Point, Set<Point>> mapLaby) {
        return mapLaby.get(de).contains(a);
    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Point pos, Map<Point, List<Point>> laby) {
        for (Point allPoint : laby.keySet()) {
            if(!this.getChemin(pos, laby).contains(allPoint)) {
                pIna.add(allPoint);
            }
        }
        return pIna;
    }

    private List<Point> getChemin(Point pos, Map<Point, List<Point>> laby) {
        chemin.add(pos);
        for (Point voisin : laby.get(pos)) {
            if(!chemin.contains(voisin)) {
                getChemin(voisin, laby);
            }
        }
        return chemin;
    }
}