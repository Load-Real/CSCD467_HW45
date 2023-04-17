public class Tester {
	public static void main(String[] args) {
		//Row and Column
		int N = 10;
		
		//2D Array
		int[][] B = { { 92, 99, 1, 8, 15, 67, 74, 51, 58, 40 }, { 98, 80, 7, 14, 16, 73, 55, 57, 64, 41 },
				{ 4, 81, 88, 20, 22, 54, 56, 63, 70, 47 }, { 85, 87, 19, 21, 3, 60, 62, 69, 71, 28 },
				{ 86, 93, 25, 2, 9, 61, 68, 75, 52, 34 }, { 17, 24, 76, 83, 90, 42, 49, 26, 33, 65 },
				{ 23, 5, 82, 89, 91, 48, 30, 32, 39, 66 }, { 79, 6, 13, 95, 97, 29, 31, 38, 45, 72 },
				{ 10, 12, 94, 96, 78, 35, 37, 44, 46, 53 }, { 11, 18, 100, 77, 84, 36, 43, 50, 27, 59 } };
		
		Solver solver = new Solver(B);
		
		printBn(solver.returnResult(), N);
	}
	
	public static void printBn(double[][] Bn, int N) {
		System.out.println("Bn = {");

        for (int i = 0; i < Bn.length; i++) {
            for (int j = 0; j < Bn.length; j++) {
                System.out.print(String.format("%6f", Bn[i][j]) + " ");
            }
            System.out.println();
        }
        
        
        System.out.print("} ");
        System.out.println();
	}
}
