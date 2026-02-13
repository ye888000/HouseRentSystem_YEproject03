package HouseRentSystem_YEproject03.service;

import HouseRentSystem_YEproject03.domain.House;

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


    // ・物件情報を保持するための固定長配列
    // ・コレクションクラスではなく配列を選択したのは、複雑なフレームワークを使用せず、最も基本的なデータ構造で「複数オブジェクトを管理できる」業務層を実装するためである。
    public class HouseService {
    private House[] houses;
    private int houseNums = 1;
    private int idCounter = 1;

    // ・物件情報を保持するための固定長配列
    // ・コレクションクラスではなく配列を選択したのは、複雑なフレームワークを使用せず、最も基本的なデータ構造で「複数オブジェクトを管理できる」業務層を実装するためである。
    public HouseService(int size) {
        houses = new House[size];
        houses[0] = new House(1, "jack", "020554588", "墨田区", 72000, "未出租/未賃貸");
    }

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

    //・新しい物件情報を追加する：IDは外部から渡さず、ここで自動増加させることでデータ重複を防ぐ。
    // ・現在は固定長配列を使用しているため、満杯の場合は追加しない。必要に応じて将来的に拡張処理を追加することも可能。
    public boolean add(House newHouses) {
        if (houseNums == houses.length) {
            System.out.println("数组已满，不能再添加了.../配列が満杯のため、これ以上追加できません...");
            return false;
        }
        newHouses.setId(++idCounter);
        houses[houseNums++] = newHouses;
        return true;
    }

       //現在登録されているすべての物件データを取得する。
       //（ただし、この設計にはリスクがあり、外部から配列内容が変更される可能性がある）
        public House[] list(){
            return houses;
        }
}

