import controller.ServerController;
import controller.SystemController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.implementations.BossRepository;
import repository.implementations.CompanyRepository;
import repository.implementations.EmployeeRepository;
import repository.implementations.TaskRepository;
import repository.interfaces.IBossRepository;
import repository.interfaces.ICompanyRepository;
import repository.interfaces.IEmployeeRepository;
import repository.interfaces.ITaskRepository;
import service.IService;
import service.Service;

public class Main extends Application {
    private static SessionFactory sessionFactory;

    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public IService initService(){
        ICompanyRepository companyRepository = new CompanyRepository(sessionFactory);
        IBossRepository bossRepository = new BossRepository(sessionFactory);
        IEmployeeRepository employeeRepository = new EmployeeRepository(sessionFactory);
        ITaskRepository taskRepository = new TaskRepository(sessionFactory);

        return new Service(companyRepository, bossRepository, employeeRepository, taskRepository);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Server");
                alert.setHeaderText("Exit");
                alert.setContentText("Good bye!");
                alert.showAndWait();

                close();
                Platform.exit();
                System.exit(0);
            }
        });

        initialize();
        IService service = initService();

        primaryStage.setTitle("Server");
        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(
                getClass().getResourceAsStream("views/serverPage.fxml")
        );

        ServerController serverController = loader.getController();

        serverController.setService(service);

        loader.setController(serverController);

        Scene scene = new Scene(pane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
