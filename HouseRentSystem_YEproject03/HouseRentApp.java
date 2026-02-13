package Houserent_system_YEproject03;

import Houserent_system_YEproject03.view.HouseView;

/**
 * 【App層】
 *本クラスはアプリケーションの「エントリーポイント」の責務のみを担う。
 *起動ロジックを簡潔に保ち、実際の画面制御は HouseView クラスに委譲している。この設計により、プログラムの「起動」と「実行ロジック」を明確に分離している。
 *
 * 【設計意図】
 * ・アプリケーションのエントリーポイント
 * ・UI 層を起動し、プログラムの実行を開始する
 *
 * 【責務範囲】
 * ・プログラムの起動を制御する
 * ・プログラムライフサイクルの起点となる
 *
 * 【設計上の取捨】
 * ・View を直接インスタンス化している。構造はシンプルだが目的は明確である
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
 */

public class HouseRentApp {
    public static void main(String[] args){
        new HouseView().MainMenu();
        //メイン処理終了後に終了メッセージを出力し、プログラムのライフサイクルが終了したことを明確にするためのものである。
        System.out.println("=====あなたは賃貸住宅システムを終了しました====");
    }
}


