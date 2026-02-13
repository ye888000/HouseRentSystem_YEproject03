package Houserent_system_YEproject03.domain;

/**
 * 【House層】
 * 賃貸管理システムにおける「物件（House）」を表すドメインモデルクラス
 *
 * 【設計意図】
 * 「房屋」という現実世界の概念を、1つのJavaクラスで集中的に表現する。
 * そのため、Houseクラスの設計においては、過度に複雑な設計を導入するのではなく、
 * データ構造の明確さ、責務の単一性、可読性の高さを優先している。
 *
 * 【責務範囲】
 * ・物件番号、家主情報、連絡先、住所、家賃、賃貸状態のデータを保持する
 * ・getter / setter を通じて他の層（Service、View）へアクセス手段を提供する
 * ・後続の出力処理のために統一された文字列表現を提供する
 *
 * 【設計上の取捨】
 * ・本プロジェクトでは、機能の網羅性よりも構造の明確さを重視している
 * ・状態を String で表現しているのは、初学段階における修正およびテストを容易にするためである
 * ・デバッグの利便性を考慮し、setter を保持している
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
 */


public class House {
    // 異なる物件を識別するためのデータ番号。
    // カプセル化されたアクセス方式を通じて、将来的なルール追加やバリデーション拡張の余地を確保している。
    // （形式：番号  家主  電話番号  住所  月額家賃  状態（未賃貸／賃貸中））
    private int id;
    private String name;//家主氏名：現在は文字列形式で管理）
    private String phonenumber;//連絡先電話番号:形式の違いに対応するため文字列で保持する。
    private String address;//物件住所:住所形式が多様であることを考慮し、文字列で表現する。
    private int rent;//月額家賃:整数型で管理する。
    private String state;//賃貸状態：賃貸中と未賃貸の2種類を想定し、文字列で表現している。

    // 本メソッドはすべての必須フィールドを受け取るために設計されている。オブジェクト生成時に必要な情報を一括で渡すことで、不完全なオブジェクトの生成を防止する。
    public House(int id, String name, String phonenumber, String address, int rent, String state) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.rent = rent;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    // 調査したところ、実際の業務ではidは通常自由に変更できない。ここでsetterを残しているのは、実践を通してカプセル化の概念を理解するためである。
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phonenumber;
    }

    public void setPhone(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // ・Houseオブジェクトをコンソール表示に適した文字列形式へ変換する
    // ・toStringをオーバーライドすることで、一覧表示時のフォーマット記述の重複を減らし、表示ロジックを簡潔に保つ。
    // ・番号を2桁で統一フォーマットすることで、出力の整列を容易にする。
    @Override
    public String toString() {

        return String.format("%02d", id) +
                "\t\t" + name +
                "\t\t" + phonenumber +
                "\t\t" + address +
                "\t\t" + rent +
                "\t" + state;
    }
}


