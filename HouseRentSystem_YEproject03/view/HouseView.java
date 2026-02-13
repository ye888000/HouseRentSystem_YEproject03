package HouseRentSystem_YEproject03.view;

import HouseRentSystem_YEproject03.domain.House;
import HouseRentSystem_YEproject03.service.HouseService;
import HouseRentSystem_YEproject03.utils.Utility;

/**
 *【View 層】
 *本クラスはView層に属する。コンソールとの対話および処理フローの制御のみを担当し、業務ロジックの処理責任は負わない。
 *また、すべてのデータ操作をService層へ委譲することで、レイヤー構造を明確に保つ。
 *
 *【設計意図】
 *·表示処理と業務処理を分離することで、各層の責務を明確にする。
 *·業務処理はすべてService層に任せ、View層は表示のみを担当し、エンティティデータの変更はView層では実装しない。
 *·入力検証はUtilityに統一して担当させる。
 *
 *【責務範囲】
 *·メインメニューの表示とプログラムループの制御
 *·ユーザーからの入力値の受け取りと、Service層への橋渡し
 *·処理結果（成功・失敗）の画面表示
 *
 *@author ye888000
 *@version 1.0
 *@since 2026-02-11
 */


    public class HouseView{
    //·プログラムをユーザーが手動で終了するまで継続実行できるように、ブール変数で制御している。
    //·あちこちでbreakを使用する方法と比べ、この方法の方が処理の流れがより一貫しており、より明確になる。
    private boolean loop=true;
    private char key=' ';
    private HouseService houseService=new HouseService(10);

    //·住所ではなく家賃のみを変更できるようにするため、設計上、空入力を「元の値を保持する」こととして扱っている。
    //·ユーザーが新しい内容を入力せずそのままEnterキーを押した場合、プログラムは「変更なし」と判断し、データの再入力の手間を減らすことができる。
    //·フィールド単位での部分更新を実現できる。
    public void update(){
        System.out.println("==========物件情報を修正==========");
        System.out.println("正する物件の番号を選択してください（-1で終了）");
        int updateId=Utility.readInt();
        if(updateId==-1){
            System.out.println("==========物件情報の修正を取り消しました==========");
            return;
        }

        House house=houseService.findById(updateId);
        if(house==null){
            System.out.println("==========修正対象の物件番号は存在しません...==========");
            return;
        }
        System.out.print("氏名("+house.getName()+"):");
        String name = Utility.readString(8,"");
        if(!"".equals(name)){
            house.setName(name);
        }
        System.out.print("電話番号：(" + house.getPhone()+"):");
        String phone=Utility.readString(12,"");
        if(!"".equals(phone)){
            house.setPhone(phone);
        }
        System.out.print("住所：("+house.getAddress()+"):");
        String address=Utility.readString(18,"");
        if(!"".equals(address)){
            house.setAddress(address);
        }
        System.out.print("家賃：("+house.getRent()+"):");
        int rent=Utility.readInt(-1);
        if(rent!=-1){
            house.setRent(rent);
        }
        System.out.print("状態：("+house.getState()+"):");
        String state=Utility.readString(3,"");
        if(!"".equals(state)){
            house.setState(state);
        }
    }

    public void findHouse(){
        System.out.println("==========物件情報を検索==========");
        System.out.println("検索するIDを入力してください:");
        int findId=Utility.readInt();

        House house=houseService.findById(findId);
        if (house!=null){
            System.out.println(house);
        }else{
            System.out.println("=========検索対象の物件IDは存在しません=========");
        }
    }

    //ツールクラスを呼び出して二重確認を行い、ユーザーの誤操作を防止する。
    public void exit(){
        char c=Utility.readConfirmSelection();
        if(c=='Y'){
            loop=false;
        }
    }

     //·確認メカニズムにより誤削除を防止する。
     //·実際の使用において、削除成功または削除中止のいずれの場合でも、ユーザーへ即時フィードバックを行う。
    public void delHouse(){
        System.out.println("==========物件を削除==========");
        System.out.println("削除する物件の番号を入力してください（-1で終了）:");
        int delId=Utility.readInt();
        if(delId==-1){
            System.out.println("==========物件情報の削除を取り消しました==========");
            return;
        }

        char choice = Utility.readConfirmSelection();

        if (choice=='Y'){
            if (houseService.del(delId)) {
                System.out.println("=========物件情報の削除に成功しました=========");
            }else{
                System.out.println("=========物件番号が存在しないため、削除に失敗しました==========");
            }
        }else{
            System.out.println("==========物件情報の削除を取り消しました==========");
        }
    }

    //Serviceに追加する際、新しいIDは自動的に割り当てられ、ユーザーに入力させないようにしている。
    //これにより、手動入力によるミスを減らし、実際の使用環境で発生しうるデータ管理の混乱を軽減できる。
    public void addHouse(){
        System.out.println("-----------物件を追加----------");
        System.out.println("氏名：");
        String name=Utility.readString(8);
        System.out.println("電話：");
        String phonenumber=Utility.readString(12);
        System.out.println("住所：");
        String address=Utility.readString(16);
        System.out.println("月額家賃：");
        int rent=Utility.readInt();
        System.out.println("状態：");
        String state=Utility.readString(3);

        House newHouse=new House(0,name,phonenumber,address,rent,state);
        if(houseService.add(newHouse)){
            System.out.println("-----------物件追加成功----------");
        }else{
            System.out.println("-----------物件追加失敗----------");
        }
    }

     //·nullを終了マーカーとして設計している。最初のnullに遭遇した時点でループを終了できるため、不要な計算リソースを節約できる。
     //·listHousesは住宅一覧のデータ出力のみを担当し、その他の機能はServiceに委譲する。
    public void listHouses(){
        System.out.println("-----------物件一覧----------");
        System.out.println("番号\\t\\t所有者\\t\\t電話\\t\\t\\t\\t住所\\t\\t月額家賃\\t\\t状態（賃貸中または未賃貸）");
        House[] houses=houseService.list();
        for(int i=0;i<houses.length;i++){
            if (houses[i]==null){
                break;
            }
            System.out.println(houses[i]);
        }
        System.out.println("-----------物件一覧表示終了-----------");
    }

     //·このループによって画面全体のプログラム「ライフサイクル」を制御できる。
     //·メニュー分岐のロジックを明確にし、追跡しやすくする。
    public void MainMenu(){
        do{
            System.out.println("\n-----------賃貸住宅システムメニュー----------");
            System.out.println("\t\t\t1 新規物件追加");
            System.out.println("\t\t\t2 物件検索");
            System.out.println("\t\t\t3 物件情報削除");
            System.out.println("\t\t\t4 修物件情報修正");
            System.out.println("\t\t\t5 物件情報表示");
            System.out.println("\t\t\t6 終了");
            System.out.println("選択してください（1-6）：");

            key=Utility.readChar();

            switch (key){
                case '1':
                    addHouse();
                    break;
                case '2':
                    findHouse();
                    break;
                case '3':
                    delHouse();
                    break;
                case '4':
                    update();
                    break;
                case '5':
                    listHouses();
                    break;
                case '6':
                    char c = Utility.readConfirmSelection();
                    exit();
                    loop=false;
                    break;
            }
        } while(loop);
    }
}


