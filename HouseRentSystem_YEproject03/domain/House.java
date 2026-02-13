package Houserent_system_YEproject03.domain;

/**
 * 【House層】
 * 賃貸管理システムにおける「物件（House）」を表すドメインモデルクラス
 *
 * 【設計意図】
 * 把「房屋」这个现实世界中的概念，用一个Java类集中表达。
 * 所以在设计House类时，优先保证数据结构清晰、责任单一、可读性高，而不是过度引入复杂设计
 *
 * 【責務範囲】
 * ・保存房屋编号、房主信息、联系方式、地址、租金、出租状态的数据
 * ・通过 getter / setter 向其他层（Service、View）提供访问接口
 * ・为接下来的输出提供统一的字符串格式
 *
 * 【設計上の取捨】
 * ・本项目では、機能の網羅性よりも構造の明確さを重視している
 * ・使用String表示状态，是便于初学阶段修改和测试
 * ・保留了setter便于调试。
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
 */

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
    //用于区分不同房屋的数据编号。
    //通过封装访问方式，为后续增加规则或校验保留调整空间。
    //（格式为：编号  房主  电话号码  地址  月租  状态（未出租/已出租））
    // 異なる物件を識別するためのデータ番号。
    // カプセル化されたアクセス方式を通じて、将来的なルール追加やバリデーション拡張の余地を確保している。
    // （形式：番号  家主  電話番号  住所  月額家賃  状態（未賃貸／賃貸中））
    private int id;
    private String name;//房主姓名（当前以字符串形式管理）/家主氏名（現在は文字列形式で管理）
    private String phonenumber;// 联系电话：为兼容格式差异，使用字符串保存/連絡先電話番号：形式の違いに対応するため文字列で保持する
    private String address;// 房屋地址：由于考虑到地址格式具有多样性，所以使用字符串表示/物件住所：住所形式が多様であることを考慮し、文字列で表現する
    private int rent;//月額家賃：选用整数进行管理/月額家賃：整数型で管理する
    private String state;// 出租状态：出租状态设定为已出租和未出租这两种状态，选用字符串表示会比较合适/賃貸状態：賃貸中と未賃貸の2種類を想定し、文字列で表現している

    //设计该方法是为了接收所有必要字段。通过在创建对象时一次性传入完整信息，从而避免产生信息不完整的对象。
    // 本メソッドはすべての必須フィールドを受け取るために設計されている。
    // オブジェクト生成時に必要な情報を一括で渡すことで、不完全なオブジェクトの生成を防止する。
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

    //通过查找资料，得知在实际业务中id通常不允许被随意修改。之所以在这里保留setter，主要用于在实践中理解封装概念。
    // 調査したところ、実際の業務ではidは通常自由に変更できない。
    // ここでsetterを残しているのは、実践を通してカプセル化の概念を理解するためである。
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

    //・将 House 对象转换为适合控制台显示的字符串形式
    //・重写 toString 是为了在列表展示时减少格式化代码的重复书写，让显示逻辑保持简洁。
    //・编号统一格式化为两位数，便于对齐输出。
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

