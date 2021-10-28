import javax.naming.Name;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] names = {"Bob", "Mary", "Steve", "Derek", "Mary", "Derek", "Joe", "Derek", "Nicole", "Mary"};
        String[] lastNames = {"Jones", "Ford", "Akers", "Smith", "Giles", "Smith", "Caiu", "Jones", "Jones", "Stepp"};
        System.out.println("Exercício 1: " + commonFirstName(Arrays.asList(names), Arrays.asList(lastNames)));

        Integer[] C = {1, 2, 4, 6, 8, 10, 12, 14};
        System.out.println("Exercício 2: " + mistery(C, C.length, 10));

        Tree<Integer> numbers = new Tree<>();
        numbers.insert(80);
        numbers.insert(70);
        numbers.insert(89);
        numbers.insert(60);
        numbers.insert(75);
        numbers.insert(85);
        numbers.insert(95);
        numbers.insert(73);
        numbers.insert(82);
        numbers.insert(87);
        numbers.insert(93);
        numbers.insert(115);
        numbers.insert(81);
        numbers.insert(88);
        numbers.insert(150);
        HashSet<Integer> set1 = new HashSet<>(); set1.add(81); set1.add(88); set1.add(150);
        HashSet<Integer> set2 = new HashSet<>(); set2.add(81); set2.add(82); set2.add(85);
        HashSet<Integer> set3 = new HashSet<>(); set3.add(60); set3.add(81); set3.add(115);
        System.out.println("Exercício 3: " + numbers.findLCA(set1) + " " + numbers.findLCA(set2) + " " + numbers.findLCA(set3));

        Metro metro = new Metro();
        System.out.println("Exercício 4: " + Arrays.toString(metro.pathSameLine("Trindade", "Dragão", "Azul").toArray()));

        System.out.printf("Exercício 5:\n" +
                "Level = 3\n" +
                "List -> " + Arrays.toString(getElemsLevel(3).toArray()) + "\n" +
                "Level = 4\n" +
                "List -> " + Arrays.toString(getElemsLevel(4).toArray()) + "\n");
    }

    public static String commonFirstName(List<String> names, List<String> lastNames) {
        Map<String, HashSet<String>> namesMap = new HashMap<>();

        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            String lastName = lastNames.get(i);
            if (!namesMap.containsKey(name)) namesMap.put(name, new HashSet<>());
            namesMap.get(name).add(lastName);
        }

        Map.Entry<String, HashSet<String>> mostPopular = null;

        for (Map.Entry<String, HashSet<String>> fullName : namesMap.entrySet()) {
            int numLastNames = fullName.getValue().size();
            if (mostPopular == null || numLastNames > mostPopular.getValue().size()) mostPopular = fullName;
        }

        return mostPopular.getKey();
    }

    public static int mistery(Integer[] a, int n, Integer x) {

        if (a[n - 1] < x)
            return n;

        if (a[0] >= x)
            return 0;

        int l = 0, u = n - 1;
        while (l < u) {
            int m = (l + u) / 2;
            if (a[m] < x)
                l = m + 1;
            else
                u = m;
        }
        return l;
    }

    public static List<Integer> getElemsLevel(int level) {
        int[] arr = {3,5,9,6,8,20,10,12,18};

        List<Integer> elems = new ArrayList<>();

        int index = 0;
        for (int i = 0; i < level - 1; i++) {
            index += Math.pow(2, i);
        }

        int levelSize = (int) Math.pow(2, level-1) + index;

        while (index < arr.length && index < levelSize) {
            elems.add(arr[index]);
            index++;
        }

        return elems;
    }
}
