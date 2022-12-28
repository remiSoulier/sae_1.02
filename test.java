public class test {
    public static void main(String[] args) {
        boolean[][] t= {{false,true,false,true},
                        {false,true,false,false},
                        {false,false,true,false},
                        {true,false,false,true}};
        boolean sym = true;
        for (int i =0;i<t.length;i++){
            for (int j =0;j<t[i].length;j++){
                if (t[i][j]!=t[j][i]){
                    sym=false;
                }
            }
        }
        System.out.println(sym);
    }
}
