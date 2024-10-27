import java.util.Arrays;
import java.util.Random;

class Box implements Comparable<Box> {
    int height, width, depth, area;

    public Box(int height, int width, int depth) {
        this.height = height;
        this.width = Math.max(width, depth);
        this.depth = Math.min(width, depth);
        this.area = this.width * this.depth;  // Base area
    }

    // Compare by base area - descending order
    @Override
    public int compareTo(Box other) {
        return other.area - this.area;
    }
}

public class Main {

    // calculate the maximum stack height
    public static int getMaxStackHeight(Box[] boxes, int n) {
        Box[] allRotations = new Box[n * 3];

        // all rotations of the boxes
        for (int i = 0; i < n; i++) {
            Box box = boxes[i];
            allRotations[3 * i] = new Box(box.height, box.width, box.depth);        // Original orientation
            allRotations[3 * i + 1] = new Box(box.width, box.height, box.depth);    // Rotated with width as height
            allRotations[3 * i + 2] = new Box(box.depth, box.height, box.width);    // Rotated with depth as height
        }

        // Sort the boxes by base area in descending order
        Arrays.sort(allRotations);

        int[] maxHeight = new int[n * 3];
        int maxOverallHeight = 0;

        // Initialize the DP array with each box's height as its own height
        for (int i = 0; i < n * 3; i++) {
            maxHeight[i] = allRotations[i].height;
        }

        for (int i = 1; i < n * 3; i++) {
            for (int j = 0; j < i; j++) {
                // If the current box can be stacked on top of box[j]
                if (allRotations[i].width < allRotations[j].width &&
                        allRotations[i].depth < allRotations[j].depth) {
                    maxHeight[i] = Math.max(maxHeight[i], maxHeight[j] + allRotations[i].height);
                }
            }
            maxOverallHeight = Math.max(maxOverallHeight, maxHeight[i]);
        }

        return maxOverallHeight;
    }


    public static void main(String[] args) {
        Random random = new Random();
        int startSize = 100;
        int increment = 100;
        int numTests = 10;

        for (int i = 1; i <= numTests; i++) {
            int N = startSize + (i - 1) * increment;
            Box[] boxes = new Box[N];

            // Generate random boxes for each test case
            for (int j = 0; j < N; j++) {
                int h = random.nextInt(100) + 1;
                int w = random.nextInt(100) + 1;
                int d = random.nextInt(100) + 1;
                boxes[j] = new Box(h, w, d);
            }

            long startTime = System.nanoTime();
            int maxHeight = getMaxStackHeight(boxes, N);
            long endTime = System.nanoTime();

            long experimentalTime = endTime - startTime;
            double theoreticalTime = N * N;

            System.out.printf("%d\t%d\t%.2f\t%d%n", N, experimentalTime, theoreticalTime, maxHeight);

        }
    }
}

function getMaxStackHeight(boxes, n):
Initialize allRotations array of size 3 * n

// Generate all rotations of the boxes
    for each box in boxes:
Add original orientation, rotated with width as height, and rotated with depth as height to allRotations

Sort allRotations by base area in descending order

Initialize maxHeight array with each element as the height of the corresponding box
Initialize maxOverallHeight as 0

        for i from 1 to size of allRotations:
        for j from 0 to i-1:
        if allRotations[i] can be stacked on allRotations[j]:
maxHeight[i] = max(maxHeight[i], maxHeight[j] + allRotations[i].height)

Update maxOverallHeight to max(maxOverallHeight, maxHeight[i])

    return maxOverallHeight