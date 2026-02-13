package HouseRentSystem_YEproject03.utils;

import java.util.Scanner;

/**
 * 【Utility層】
 * 本クラスはシステムの共通ツール層として、
 * コンソール入力の「バリデーション」と「例外処理」を担当する。
 * 入力ロジックを集約することで、View層の構造をより簡潔にし、
 * 不正入力が発生した場合でもシステムが安定して動作できるようにしている。
 *
 * 【設計意図】
 * ・便利で簡潔なコンソール入力メソッドを提供し、
 *   すべての入力ロジックを1つのクラスに集約することで、
 *   複数クラスでの重複実装を避ける。
 * ・入力段階で長さチェック、形式検証、例外処理を完了させ、
 *   上位層では入力の正当性を意識しなくてよいようにする。
 *
 *【責務範囲】
 * ・メニュー選択入力の検証
 * ・文字入力の取得
 * ・整数解析および例外捕捉
 * ・文字列長の制限
 * ・確認選択（Y/N）のロジック検証
 *
 * 【設計上の取捨】
 * ・呼び出しの簡潔性を優先し、staticメソッドとして設計。
 *   インスタンス生成不要で利用可能。
 * ・無限ループによる正当入力保証。
 *   すべての読み取りメソッドはwhileによる強制検証を行い、
 *   必ず正しい値を返すようにしている。
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
 */


 public class Utility {
   
        // すべての入力で同一のScannerを使用することで、
        // 複数メソッド内でのオブジェクト重複生成を防いでいる。
        private static Scanner scanner = new Scanner(System.in);

       //·入力段階で選択可能範囲を制限し、1～5のみ許可する。誤入力の場合は強制的に再入力させることで、戻り値が必ず正当であることを保証する。
       //  これによりView層はフロー制御のみを担当し、入力検証ロジックを持たなくてよい。
       //·ループ検証方式により、上位層での追加判定処理を不要にしている。
        public static char readMenuSelection() {
            char c;
            for (; ;) {
                String str = readKeyBoard(1, false);
                c= str.charAt(0);
                if (c!= '1' && c!= '2' &&
                        c!= '3' && c!= '4' && c!= '5') {
                    System.out.print("選択エラーです。もう一度入力してください:");
                } else break;
            }
            return c;
        }

        public static char readChar() {
            String str = readKeyBoard(1, false);
            return str.charAt(0);
        }

        public static char readChar(char defaultValue) {
            String str = readKeyBoard(1, true);
            return (str.length() == 0) ? defaultValue : str.charAt(0);
        }

    //·try-catchにより例外を捕捉し、ユーザーが数値以外を入力した場合の解析例外を防止する。
    //·ループを使用して、必ず整数を返すことを保証する。
        public static int readInt() {
            int n;
            for (; ; ) {
                String str = readKeyBoard(10, false);
                try {
                    n = Integer.parseInt(str);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("数値入力エラーです。もう一度入力してください:");
                }
            }
            return n;
        }


       //·支持空输入返回默认值，用于更新操作中“保持原值”的场景。可以解决“改个租金还要把所有信息重填一遍”的麻烦。
       //·将输入策略统一放在工具层处理，明确各个类的分工，也能保证代码的逻辑清晰。
       //·空入力時にデフォルト値を返す設計。更新処理における「元の値を保持する」場面に対応する。
       //（例：家賃のみ変更する際に、他項目を再入力する手間を省く）
       //·入力戦略をツール層に統一することで、各クラスの責務を明確にし、ロジックの可読性を維持する。
        public static int readInt(int defaultValue) {
            int n;
            for (; ; ) {
                String str = readKeyBoard(10, true);
                if (str.equals("")) {
                    return defaultValue;
                }

                try {
                    n = Integer.parseInt(str);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("数値入力エラーです。もう一度入力してください:");
                }
            }
            return n;
        }

        public static String readString(int limit) {
            return readKeyBoard(limit, false);
        }

        public static String readString(int limit, String defaultValue) {
            String str = readKeyBoard(limit, true);
            return str.equals("")? defaultValue : str;
        }

        //重要操作の前に強制確認を行い、誤操作を防止することを目的としている。
        public static char readConfirmSelection() {
            System.out.println("请输入你的选择(Y/N): 请小心选择/あなたの選択を入力してください(Y/N)：慎重に選択してください");
            char c;
            for (; ; ) {
                String str = readKeyBoard(1, false).toUpperCase();
                c=str.charAt(0);
                if (c == 'Y' || c == 'N') {
                    break;
                } else {
                    System.out.print("選択エラーです。もう一度入力してください:");
                }
            }
            return c;
        }

        //すべての入力メソッドは最終的に本メソッドを呼び出すため、ここで長さ制限と空入力処理を統一管理するのが適切である。
        //将来的にルール変更が必要な場合も、この層のみ修正すればよいため、コードの保守性が向上する。
        private static String readKeyBoard(int limit,boolean blankReturn) {
            String line="";

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line.length() == 0) {
                    if (blankReturn) return line;
                }
                if (line.length() < 1 || line.length() > limit) {
                    System.out.print("入力長さ（"+limit+"以下である必要があります）エラーです。もう一度入力してください：");
                    continue;
                }
                break;
            }
            return line;
        }
    }

