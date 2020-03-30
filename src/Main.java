import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static HashMap getAtoms(){
        HashMap<String,Atom> atoms = null;
        try {
            atoms = new HashMap<>();
            File file = new File("src/numericDensities");
            FileReader in = new FileReader(file);
            BufferedReader br = new BufferedReader(in);



            String line;
            String[] out;

            while ((line = br.readLine()) != null) {

                Atom atom;
                out = line.split(",");


                atom = new Atom(out[0].toLowerCase().trim(), out[1], Double.parseDouble(out[2]),
                        Double.parseDouble(out[3]), Double.parseDouble(out[4]),
                        out[5], out[6], out[7]);
                atoms.put(atom.getName(),atom);

            }

            in.close();
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return atoms;
    }

    public static HashMap getAtomRadii(){
        HashMap<String,AtomRadii> atoms = null;
        try {
            atoms = new HashMap<>();
            File file = new File("src/atomicRadii");
            FileReader in = new FileReader(file);
            BufferedReader br = new BufferedReader(in);



            String line;
            String[] out;

            while ((line = br.readLine()) != null) {

                AtomRadii atom;
                out = line.split(",");



                atom = new AtomRadii(Double.parseDouble(out[0]), out[1], out[2],
                        Double.parseDouble(out[3]), Double.parseDouble(out[4]),
                        Double.parseDouble(out[5]), Double.parseDouble(out[6]),
                        Double.parseDouble(out[7]), Double.parseDouble(out[8]));
                atoms.put(atom.getName(),atom);

            }

            in.close();
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return atoms;
    }

    //TODO: Wrong rekkefølge
    public static double calculateDensity(double weight, double radius){
        System.out.println("Weight: " + weight + " radius: " + radius);
        double density = -1;
        double u = 1.66053906660 * Math.pow(10,-24.0); //atom weight in gram

        if(weight == -1 || radius == -1){
            return density;
        } else {
            radius = radius * Math.pow(10,-10);
            density = ((weight * u) * 3) / (4 * Math.PI * Math.pow(radius, 3)); //We change radius into cm
        }
        return density;
    }

    public static void main(String[] args) {
        //getAtoms();
        //getAtomRadii();

        HashMap<String, Atom> atoms = getAtoms();
        HashMap<String, AtomRadii> atomRadii = getAtomRadii();

        try {
            File file = new File("src/atomicWeight");
            FileReader in = new FileReader(file);
            BufferedReader br = new BufferedReader(in);



            String line;
            String[] out;

            while ((line = br.readLine()) != null) {

                out = line.split(",");

                atoms.get(out[1].toLowerCase().trim()).setWeight(Double.parseDouble(out[3]));
            }

            in.close();
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }


        /*
                for (Map.Entry me : atoms.entrySet()) {
            System.out.println("Key: "+me.getKey() + " & Value: " + me.getValue().toString());
        }
         */

    /*
        Iterator hmIterator = atoms.entrySet().iterator();

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            System.out.println(mapElement.getKey() + " : " + mapElement.getValue());
        }
     */

        //atoms.forEach((key, value) -> System.out.println(value.getName() + " " + value.getWeight()));


        atoms.forEach((key, value) -> {
            //System.out.println(value.getName() + " " + value.getWeight());
            if(atomRadii.get(value.getName()) != null){
                System.out.println(value.getName() + " " + calculateDensity(value.getWeight(), atomRadii.get(value.getName()).getCalculated()));
            }
        });


        //atoms.forEach((key, value) -> System.out.println(calculateDensity(value.getWeight(), atomRadii.get(key).getCalculated())));


   /*
    //Get all weights based on empirical radius
        for(int i = 0; i < atoms.size(); i++){ //atoms and atomRadii contains the same length because of same periodic table
            System.out.println(
                    atoms.get(i).getName() + ": " + calculateDensity(atoms.get(i).getWeight(), atomRadii.get(i).getEmpirical())
            );
        }
    */


    /*
          //Get all weights based on calculated radius
        for(int i = 0; i < atoms.size(); i++){ //atoms and atomRadii contains the same length because of same periodic table
            System.out.println(
                    atoms.get(i).getName() + ": " + calculateDensity(atoms.get(i).getWeight(), atomRadii.get(i).getCalculated())
            );
        }
     */





    /*
        double avogadros = 6.022 * Math.pow(10, 23); //Avogadros number

        //
        for(int i = 0; i < atoms.size(); i++){ //atoms and atomRadii contains the same length because of same periodic table
            System.out.println(
                    calculateDensity(atoms.get(i).getWeight(), atomRadii.get(i).getCalculated()) / (atoms.get(i).getWeight()) * avogadros
            );
        }
     */


    }

}
