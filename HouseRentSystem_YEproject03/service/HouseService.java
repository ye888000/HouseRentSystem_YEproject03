package Houserent_system_YEproject03.service;

import Houserent_system_YEproject03.domain.House;
/**
 * 【Service層】
 * 本类作为业务处理层，
 * 负责 House 数据的新增、删除、查询等基础操作，
 * 通过分层方式使结构更加清晰。
 *
 * 【設計意図】
 * 目的是将数据管理逻辑集中在此类中，实现界面显示与数据处理的分离。
 * 同时划分了每个类的“职责范围”，即让 View类只管显示，Service类只管逻辑。
 *
 * 【責務範囲】
 * ・维护内部数组的结构
 * ・让该类能够根据ID查找数据
 * ・在删除数据后仍然能够保证数据连续，而不会出现数据空缺或者序号混乱的情况。
 * ・可以进行简单的ID自增
 *
 * 【設計上の取捨】
 *・使用固定长度数组，而非使用List。因为考虑到在实际使用场景下，采用数组可使数据结构与处理流程更加直观
 *・选择让ID生成逻辑集中在Service类中，这样降低整体结构复杂度
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
 */

/**
 * 【Service層】
 * 本クラスは業務処理層として、
 * Houseデータの追加、削除、検索などの基本操作を担当する。
 * 分層構造を採用することで、全体構造をより明確にしている。
 *
 * 【設計意図】
 * データ管理ロジックを本クラスに集約し、画面表示とデータ処理を分離することを目的としている。
 * また、各クラスの「責務範囲」を明確にし、Viewクラスは表示のみ、Serviceクラスはロジックのみを担当する構成としている。
 *
 * 【責務範囲】
 * ・内部配列の構造を管理する
 * ・IDに基づいてデータを検索できるようにする
 * ・削除後もデータの連続性を維持し、空白や番号の混乱が発生しないようにする
 * ・簡易的なID自動増加機能を持つ
 *
 * 【設計上の取捨】
 * ・Listではなく固定長配列を使用している。実際の使用場面を想定した際、配列の方がデータ構造と処理フローをより直感的に理解できると考えたため。
 * ・ID生成ロジックをServiceクラスに集約することで、全体構造の複雑さを抑えている
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
 */


    //·用于保存房屋信息的固定长度数组
    //·选择数组而非集合类，是为了在不使用复杂框架的前提下，用最基础的数据结构，实现一个“可管理多个对象”的业务层。
    //
    public class HouseService {
    private House[] houses;
    private int houseNums = 1;
    private int idCounter = 1;

    //・在创建 Service 时，先把数组和第一条数据准备好，这样系测试时就有可操作的数据。
    //・且暂时不考虑动态扩容，而是优先选择把增删改查的流程写清楚，便于后续检查以及用户使用。
    // ・物件情報を保持するための固定長配列
    // ・コレクションクラスではなく配列を選択したのは、複雑なフレームワークを使用せず、
    //   最も基本的なデータ構造で「複数オブジェクトを管理できる」業務層を実装するためである。
    public HouseService(int size) {
        houses = new House[size];
        houses[0] = new House(1, "jack", "020554588", "墨田区", 72000, "未出租");
    }

    //采用顺序查找方式，这样可以方便地根据房屋ID查找对应的房屋信息
    // 順次検索方式を採用し、物件IDに基づいて該当データを取得できるようにしている
    public House findById(int findId){

        for (int i=0;i<houseNums;i++){
            if (findId == houses[i].getId()) {
                return houses[i];
            }
        }
        return null;
    }

    public boolean del(int delId) {
        int index = -1;
        for (int i = 0; i < houseNums; i++) {
            if (delId == houses[i].getId()) {
                index = i;
            }
            break;
        }

        if (index == -1) {
            return false;
        }
        for (int i = index; i < houseNums - 1; i++) {
            houses[i] = houses[i + 1];
        }
        houses[--houseNums] = null;
        return true;
    }

    //·添加新的房屋信息：ID不从外部传入，而是在这里自动递增，避免出现数据重复。
    //·当前使用固定长度数组，如果数据已满，则不再添加。如果需要对数据进行扩容，也可以进行调整
    // ・新しい物件情報を追加する：IDは外部から渡さず、ここで自動増加させることでデータ重複を防ぐ。
    // ・現在は固定長配列を使用しているため、満杯の場合は追加しない。
    //   必要に応じて将来的に拡張処理を追加することも可能。
    public boolean add(House newHouses) {
        if (houseNums == houses.length) {
            System.out.println("数组已满，不能再添加了...");
            return false;
        }
        newHouses.setId(++idCounter);
        houses[houseNums++] = newHouses;
        return true;
    }

       //用于获取目前登记的所有房屋数据。（但是这个设计有风险，因为可能在外部会不小心改掉数据）
       // 現在登録されているすべての物件データを取得する。
       // （ただし、この設計にはリスクがあり、外部から配列内容が変更される可能性がある）
        public House[] list(){
            return houses;
        }
}
