public class test {
    public static void main(String[] args) {
        boolean[][] m= {{false,true,false},
                        {true,false,true},
                        {false,true,true},};

        boolean[][] m2= {{true,true,true},
                        {false,true,true},
                        {false,false,false},};


        for (int i=0; i<m.length; i++) {
            for (int j=0; j<m.length; j++) {
                if (m[i][j]==true) {
                    for (int k=0; k<m.length; k++) {
                        if (m[j][k]==true && m[i][k]==false) {
                            System.out.println("n'est pastransitive !  i = "+i+" ; j = "+j+" ; k = "+k);
                        }
                    }
                }
            }
        }
        System.out.println("est Transitive ! ");
    }
}
