import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Solver {
	int N, globalMax, progress;
	int[][] B;
	double[][] Bn;
	CyclicBarrier barrier;
	
	public Solver(int[][] B) {
		this.B = B;
		this.N = B.length;
//		this.mDepth = B[0].length;
		this.Bn = new double[N][N];
		
		this.barrier = new CyclicBarrier(N, new Runnable() {
						public void run() {
							updateProgress();
						}
		});
		for (int i = 0; i < N; ++i) {
			new Thread(new Worker(B[i], i, barrier)).start();
		}
		
		waitUntilDone();
	}
	
	private synchronized void updateGlobalMax(int currentMax) {
		if (globalMax < currentMax) {
			globalMax = currentMax;
		}
	}
	
	class Worker implements Runnable {
		int[] myRow;
		int rowID;
		CyclicBarrier barrier;
		
		Worker(int[] row, int rowID, CyclicBarrier barrier) {
			this.myRow = row;
			this.rowID = rowID;
			this.barrier = barrier;
		}
		
		public void run() {
			try {
				processRow();
				barrier.await();
				normalizeRow();
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		
		public void normalizeRow() {
			double[] Bn = new double[N];
			
			for (int i = 0; i < N; i++) {
				Bn[i] = (myRow[i] * 1.0) / globalMax;
			}
			updateResult(Bn, rowID);
		}
		public void processRow() {
			int currentMax = -1;
			
			for (int i = 0; i < myRow.length; i++) {
				if (myRow[i] > currentMax) {
					currentMax = myRow[i];
				}
			}
			updateGlobalMax(currentMax);
		}
	}
	
	public synchronized void updateProgress() {
		progress++;
		notifyAll();
	}
	
	public synchronized void updateResult(double[] Bn, int rowID) {
		this.Bn[rowID] = Bn;
	}
	
	public double[][] returnResult() {
		return Bn;
	}
	
	public synchronized void waitUntilDone() {
		while (progress < 2) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
