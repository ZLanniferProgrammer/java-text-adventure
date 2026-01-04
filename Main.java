import java.util.*;

public class Main {

    // ================= Utility =================
    static int sumDigits(String studentId) {
        int sum = 0;
        for (char c : studentId.toCharArray()) {
            if (Character.isDigit(c)) sum += c - '0';
        }
        return sum;
    }

    static String makeSignature(String studentId, int score) {
        int checksum = 0;
        for (char c : studentId.toCharArray()) {
            checksum = (checksum * 31 + c) % 100000;
        }
        checksum = (checksum + score * 97) % 100000;
        return "SIG-" + String.format("%05d", checksum);
    }

    static int askChoice(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("เลือกไม่ถูกต้อง (" + min + "-" + max + ")");
        }
    }

    // ================= Mini Mission =================
    static boolean miniMission(Scanner sc) {
        System.out.println("\n[Mini Mission] ตอบให้ถูกอย่างน้อย 2 ข้อ");

        int correct = 0;

        System.out.print("Q1: 4 + 6 = ");
        if (sc.nextLine().equals("10")) correct++;

        System.out.print("Q2: Java เป็นภาษาเชิงวัตถุหรือไม่ (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) correct++;

        System.out.print("Q3: คำสั่งแสดงผลใน Java คือ println ใช่หรือไม่ (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) correct++;

        return correct >= 2;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("กรอกรหัสนักศึกษา: ");
        String studentId = sc.nextLine();

        int key = sumDigits(studentId) % 97;
        int energy = (key * 7 + 13) % 100;
        int logic  = (key * 11 + 5) % 100;
        int luck   = (key * 17 + 19) % 100;

        int score = 0;
        ArrayList<String> items = new ArrayList<>();

        System.out.println("\n[Profile]");
        System.out.println("Energy=" + energy + " Logic=" + logic + " Luck=" + luck);

        // ================= Scene 1 =================
        System.out.println("\n[Scene 1] ห้องแลบตอนดึก");
        System.out.println("AI: \"ตรวจพบวัตถุลึกลับ\"");
        System.out.println("AI: \"การเลือกของคุณจะส่งผลต่ออนาคต\"");
        System.out.println("คุณ: \"ผมต้องคิดให้รอบคอบ\"");
        System.out.println("AI: \"ระบบพร้อมบันทึก\"");
        System.out.println("AI: \"โปรดตัดสินใจ\"");

        int c1 = askChoice(sc,
                "1) เปิดกล่อง  2) สแกนก่อน  3) เดินหนี : ", 1, 3);

        if (c1 == 1) {
            System.out.println("AI: \"คุณเลือกความเสี่ยง\"");
            items.add("Lucky Charm");
            score += 10;

        } else if (c1 == 2) {
            System.out.println("AI: \"คุณเลือกการวิเคราะห์\"");
            items.add("Logic Lens");
            score += 15;

        } else {
            System.out.println("AI: \"คุณเลือกความปลอดภัย\"");
            score += 5;
        }

        // ================= Scene 2 (Trap Added) =================
        System.out.println("\n[Scene 2] ทางเดินแยก");
        int c2 = askChoice(sc, "เลือกทาง 1 หรือ 2: ", 1, 2);

        if (c2 == 1) {
            System.out.println("AI: \"ต้องใช้พลังงาน\"");

            // กับดัก: เดินหนีมาก่อน + เลือกทาง 1
            if (c1 == 3) {
                System.out.println("⚠ คุณตกหลุมกับดัก!");
                System.out.println("AI: \"การหลีกเลี่ยงโดยไม่วางแผนทำให้พลาด\"");
                score -= 10;
            } else {
                score += (energy > 50) ? 15 : 7;
            }

        } else {
            System.out.println("AI: \"ต้องใช้ตรรกะ\"");
            score += (logic > 50) ? 15 : 6;
        }

        // ================= Mini Mission =================
        boolean miniMissionPassed = miniMission(sc);

        if (miniMissionPassed) {
            System.out.println("✔ ภารกิจย่อยสำเร็จ");
            score += 20;
        } else {
            System.out.println("✘ ภารกิจย่อยล้มเหลว");
            score += 5;
        }

        // ================= Bonus Scene =================
        if (miniMissionPassed || items.size() >= 2) {
            System.out.println("\n[Bonus Scene] ห้องทดลองลับ");
            System.out.println("AI: \"คุณพิสูจน์ความสามารถด้วยการตัดสินใจ\"");
            items.add("Bonus Chip");
            score += 25;
        }

        // ================= Use Items =================
        if (items.contains("Lucky Charm")) score += 10;
        if (items.contains("Logic Lens")) score += 10;
        if (items.contains("Bonus Chip")) score += 15;

        // ================= Result =================
        System.out.println("\n+---------------- RESULT ----------------+");
        System.out.printf("| Energy : %-3d  Logic : %-3d  Luck : %-3d |\n", energy, logic, luck);
        System.out.printf("| Items  : %-25s |\n", items);
        System.out.printf("| Score  : %-25d |\n", score);
        System.out.println("+----------------------------------------+");
        System.out.println("Signature: " + makeSignature(studentId, score));

        sc.close();
    }
}
