package Houserent_system_YEproject03.utils;

import java.util.Scanner;

/**
 * 【Utility層】
 *本类作为系统的共通工具层，负责处理控制台输入的“校验”与“异常捕获”。通过将输入逻辑集中处理，使View层结构更加简洁。并确保系统在面对非法输入时，依然能保持稳定运行。
 *
 * 【設計意図】
 *·提供便利、简单的控制台输入方法，将所有控制台输入逻辑集中到一个类中，避免在多个类中重复创建编写。
 *·在输入阶段就完成长度检查、格式校验和异常处理，让上层调用时不需要再关心输入是否合法。
 *
 * 【責務範囲】
 *·菜单选项输入校验
 *·字符输入读取
 *·整数解析与异常捕获
 *·字符串长度限制
 *·确认选择（Y/N）逻辑验证
 *
 * 【設計上の取捨】
 *·优先考虑调用简洁性，因此使用static静态方法设计，调用简单，无需实例化即可使用。
 *·无限循环保证合法输入。所有读取方法均采用while强制校验，确保返回值一定合法。
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
*/

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
 * 【責務範囲】
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
        //
        //每次输入都使用同一个 Scanner，这样可以避免在多个方法中重复创建对象。
        // すべての入力で同一のScannerを使用することで、
        // 複数メソッド内でのオブジェクト重複生成を防いでいる。

        private static Scanner scanner = new Scanner(System.in);

       //在输入阶段就限制可选范围，只允许输入 1～5，如果输入错误则强制重新输入，这样可以证返回值一定合法。
       //从而让 View 层只负责流程控制，不再处理输入合法性判断。
       //通过循环校验方式，
       //让上层不需要再做判断处理。
       // 入力段階で選択可能範囲を制限し、1～5のみ許可する。
       // 誤入力の場合は強制的に再入力させることで、
       // 戻り値が必ず正当であることを保証する。
       // これによりView層はフロー制御のみを担当し、
       // 入力検証ロジックを持たなくてよい。
       // ループ検証方式により、
       // 上位層での追加判定処理を不要にしている。
        public static char readMenuSelection() {
            char c;
            for (; ;) {
                String str = readKeyBoard(1, false);//包含一个字符的字符串
                c= str.charAt(0);
                if (c!= '1' && c!= '2' &&
                        c!= '3' && c!= '4' && c!= '5') {
                    System.out.print("选择错误，请重新输入：");
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



    //·通过 try-catch 捕获异常，防止用户输入非数字导致解析异常。
    //·使用循环确保方法一定返回整数。
    // ・try-catchにより例外を捕捉し、
    //   ユーザーが数値以外を入力した場合の解析例外を防止する。
    // ・ループを使用して、必ず整数を返すことを保証する。
        public static int readInt() {
            int n;
            for (; ; ) {
                String str = readKeyBoard(10, false);
                try {
                    n = Integer.parseInt(str);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("数字输入错误，请重新输入：");
                }
            }
            return n;
        }


     //·支持空输入返回默认值，用于更新操作中“保持原值”的场景。可以解决“改个租金还要把所有信息重填一遍”的麻烦。
     //·将输入策略统一放在工具层处理，明确各个类的分工，也能保证代码的逻辑清晰。
    // ・空入力時にデフォルト値を返す設計。
    //   更新処理における「元の値を保持する」場面に対応する。
    //   （例：家賃のみ変更する際に、他項目を再入力する手間を省く）
    // ・入力戦略をツール層に統一することで、
    //   各クラスの責務を明確にし、ロジックの可読性を維持する。

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
                    System.out.print("数字输入错误，请重新输入：");
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

        //在重要操作前强制确认，目的是防止误操作
        // 重要操作の前に強制確認を行い、
        // 誤操作を防止することを目的としている。
        public static char readConfirmSelection() {
            System.out.println("请输入你的选择(Y/N): 请小心选择");
            char c;
            for (; ; ) {
                String str = readKeyBoard(1, false).toUpperCase();
                c=str.charAt(0);
                if (c == 'Y' || c == 'N') {
                    break;
                } else {
                    System.out.print("选择错误，请重新输入：");
                }
            }
            return c;
        }

        //由于所有输入方法最终都会调用本方法，所以在此统一处理长度限制与空输入比较合适。
        //以后如果需要改动规则，只需要动这这层代码，这样可以提高了代码的可维护性。
        // すべての入力メソッドは最終的に本メソッドを呼び出すため、
        // ここで長さ制限と空入力処理を統一管理するのが適切である。
        // 将来的にルール変更が必要な場合も、
        // この層のみ修正すればよいため、
        // コードの保守性が向上する。
        private static String readKeyBoard(int limit,boolean blankReturn) {
            String line="";

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line.length() == 0) {
                    if (blankReturn) return line;
                }
                if (line.length() < 1 || line.length() > limit) {
                    System.out.print("输入长度（不能大于" + limit + "）错误，请重新输入：");
                    continue;
                }
                break;
            }
            return line;
        }
    }