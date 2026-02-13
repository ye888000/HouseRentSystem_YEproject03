package HouseRentSystem_YEproject03.view;

import HouseRentSystem_YEproject03.domain.House;
import HouseRentSystem_YEproject03.service.HouseService;
import HouseRentSystem_YEproject03.utils.Utility;
/**
 * 【View 層】
 * 本类属于View层。仅负责控制台交互与流程控制，不承担业务逻辑处理职责。同时将所有数据操作统一委托给 Service 层，以保证分层清晰。
 *
 * 【設計意図】
 * ・通过分离显示与业务处理，使各层职责明确。
 * ・让业务处理统一交给Service层，让View层只管显示，实体数据修改不在View层实现
 * ・输入校验统一由Utility负责
 *
 【責務範囲】
 * ・メインメニューの表示とループ制御
 * (负责主菜单的显示与程序循环控制)
 *
 * ・ユーザーからの入力値の受け取りと、Service層への橋渡し
 * (接收用户输入，并将其传递给Service层)
 *
 * ・処理結果（成功・失敗）の画面表示
 * (处理结果（成功或失败）的界面反馈与展示)
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
 */

/**
 * 【View 層】
 * 本クラスはView層に属する。コンソールとの対話および処理フローの制御のみを担当し、
 * 業務ロジックの処理責任は負わない。
 * また、すべてのデータ操作をService層へ委譲することで、レイヤー構造を明確に保つ。
 *
 * 【設計意図】
 * ・表示処理と業務処理を分離することで、各層の責務を明確にする。
 * ・業務処理はすべてService層に任せ、View層は表示のみを担当し、
 *   エンティティデータの変更はView層では実装しない。
 * ・入力検証はUtilityに統一して担当させる。
 *
 【責務範囲】
 * ・メインメニューの表示とプログラムループの制御
 * (メインメニューの表示とプログラムのループ制御を担当する)
 *
 * ・ユーザーからの入力値の受け取りと、Service層への橋渡し
 * (ユーザー入力を受け取り、Service層へ渡す)
 *
 * ・処理結果（成功・失敗）の画面表示
 * (処理結果（成功または失敗）の画面フィードバックおよび表示)
 *
 * @author ye888000
 * @version 1.0
 * @since 2026-02-11
 */


    public class HouseView {
    //·为了让程序能持续运行直到用户手动退出，使用了布尔变量控制，
    //·相比于随处使用break，这种方式能让程序看起来更连贯、更清晰。
    //・プログラムをユーザーが手動で終了するまで継続実行できるように、
    //  ブール変数で制御している。
    //・あちこちでbreakを使用する方法と比べ、
    //  この方法の方が処理の流れがより一貫しており、より明確になる。
    private boolean loop = true;
    private char key = ' ';
    private HouseService houseService = new HouseService(10);


    //・考虑到能只修改租金而不是地址，在设计上允许空输入表示保持原值，
    // ·如果用户不输入新内容直接回车，程序会判定为“不修改内容”，可以减少重复输入数据的麻烦。
    //・可以达到字段级局部更新
    //・住所ではなく家賃のみを変更できるようにするため、
    //  設計上、空入力を「元の値を保持する」こととして扱っている。
    //・ユーザーが新しい内容を入力せずそのままEnterキーを押した場合、
    //  プログラムは「変更なし」と判断し、
    //  データの再入力の手間を減らすことができる。
    //・フィールド単位での部分更新を実現できる。
    public void update() {
        System.out.println("==========修改房屋信息==========");
        System.out.println("请选择待修改房屋的编号（-1表示退出）");
        int updateId = Utility.readInt();
        if (updateId == -1) {
            System.out.println("==========你放弃了修改房屋信息==========");
            return;
        }

        House house = houseService.findById(updateId);
        if (house == null) {
            System.out.println("==========修改房屋信息的编号不存在...==========");
            return;
        }
        System.out.print("姓名(" + house.getName() + "): ");
        String name = Utility.readString(8, "");
        if (!"".equals(name)) {
            house.setName(name);
        }
        System.out.print("电话号码：(" + house.getPhone() + "): ");
        String phone = Utility.readString(12, "");
        if (!"".equals(phone)) {
            house.setPhone(phone);
        }
        System.out.print("地址：(" + house.getAddress() + "): ");
        String address = Utility.readString(18, "");
        if (!"".equals(address)) {
            house.setAddress(address);
        }
        System.out.print("租金：(" + house.getRent() + "): ");
        int rent = Utility.readInt(-1);
        if (rent!=-1) {
            house.setRent(rent);
        }
        System.out.print("状态：(" + house.getState() + "): ");
        String state = Utility.readString(3, "");
        if (!"".equals(state)) {
            house.setState(state);
        }
    }

    public void findHouse() {
        System.out.println("==========查找房屋信息==========");
        System.out.println("请输入要查找的id：");
        int findId = Utility.readInt();

        House house = houseService.findById(findId);
        if (house != null) {
            System.out.println(house);
        } else {
            System.out.println("=========查找房屋信息的id不存在=========");
        }
    }

    //调用工具类进行二次确认，防止用户点错
    //ツールクラスを呼び出して二重確認を行い、
    //ユーザーの誤操作を防止する。
    public void exit() {
        char c = Utility.readConfirmSelection();
        if (c == 'Y') {
            loop = false;
        }
    }

     //·通过确认机制防止误删除。
     //·在实际使用中是否删除成功，或者放弃删除，都能够实时反馈给用户。
     //・確認メカニズムにより誤削除を防止する。
     //・実際の使用において、削除成功または削除中止のいずれの場合でも、
     //  ユーザーへ即時フィードバックを行う。
    public void delHouse() {
        System.out.println("==========删除房屋==========");
        System.out.println("请输入待删除房屋的编号（-1表示退出）：");
        int delId = Utility.readInt();
        if (delId == -1) {
            System.out.println("==========放弃删除房屋信息==========");
            return;
        }

        char choice = Utility.readConfirmSelection();

        if (choice == 'Y') {
            if (houseService.del(delId)) {
                System.out.println("=========删除房屋信息成功=========");
            } else {
                System.out.println("=========房屋编号不存在，删除失败==========");
            }
        } else {
            System.out.println("==========放弃删除房屋信息==========");
        }
    }

    //添加Service时会自动分配一个新的ID，而非让用户输入，
    //样可以减少手动输入数据时可能导致的失误，也减少了可能会在实际使用场景下出现数据收录混乱的可能性。
    //Serviceに追加する際、新しいIDは自動的に割り当てられ、
    //ユーザーに入力させないようにしている。
    //これにより、手動入力によるミスを減らし、
    //実際の使用環境で発生しうるデータ管理の混乱を軽減できる。
    public void addHouse() {
        System.out.println("-----------添加房屋----------");
        System.out.println("姓名：");
        String name = Utility.readString(8);
        System.out.println("电话：");
        String phonenumber = Utility.readString(12);
        System.out.println("地址：");
        String address = Utility.readString(16);
        System.out.println("月租：");
        int rent = Utility.readInt();
        System.out.println("状态：");
        String state = Utility.readString(3);

        House newHouse = new House(0, name, phonenumber, address, rent, state);
        if (houseService.add(newHouse)) {
            System.out.println("-----------添加房屋成功----------");
        } else {
            System.out.println("-----------添加房屋失败----------");
        }
    }

     //设计将null作为终止标识。只要遇到第一个null，程序就可以直接结束循环，节省了不必要的计算资源。
     //listHouses只负责房屋列表的数据输出，而非其他功能。其他功能交给Service处理。
     //nullを終了マーカーとして設計している。
     //最初のnullに遭遇した時点でループを終了できるため、
     //不要な計算リソースを節約できる。
     //listHousesは住宅一覧のデータ出力のみを担当し、
     //その他の機能はServiceに委譲する。
    public void listHouses() {
        System.out.println("-----------房屋列表----------");
        System.out.println("编号\t\t房主\t\t电话\t\t\t\t地址\t\t月租\t\t状态（出租或未出租）");
        House[] houses = houseService.list();
        for (int i = 0; i < houses.length; i++) {
            if (houses[i] == null) {
                break;
            }
            System.out.println(houses[i]);
        }
        System.out.println("-----------房屋列表显示完毕-----------");
    }


     //·可以通过该循环控制整个界面的程序“生命周期”
     // ·使菜单分支逻辑清晰、便于追踪
     //・このループによって画面全体のプログラム「ライフサイクル」を制御できる。
     //・メニュー分岐のロジックを明確にし、追跡しやすくする。
    public void MainMenu() {
        do {
            System.out.println("\n-----------房屋出租系统菜单----------");
            System.out.println("\t\t\t1 新增房源");
            System.out.println("\t\t\t2 查找房屋");
            System.out.println("\t\t\t3 删除房屋信息");
            System.out.println("\t\t\t4 修改房屋信息");
            System.out.println("\t\t\t5 显示房屋信息");
            System.out.println("\t\t\t6 退出");
            System.out.println("请输入你的选择（1-6）：");

            key = Utility.readChar();

            switch (key) {
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
                    loop = false;
                    break;
            }
        } while (loop);
    }
}

