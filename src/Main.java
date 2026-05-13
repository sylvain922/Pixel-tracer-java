import app.Command;
import app.CommandExecutor;
import app.CommandParser;
import app.CommandResult;
import app.PixelTracerApp;
import pixel.BresenhamRasterizer;
import render.ConsoleRenderer;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        PixelTracerApp app = new PixelTracerApp();
        app.init();
        ConsoleRenderer renderer = new ConsoleRenderer(new BresenhamRasterizer());
        CommandExecutor executor = new CommandExecutor(app);
        Scanner scanner = new Scanner(System.in);

        redraw(renderer, app);

        while (true) {
            System.out.print("~> ");
            System.out.flush();
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine();
            Command cmd = CommandParser.parse(line);
            CommandResult result = executor.execute(cmd);
            switch (result) {
                case OK_REDRAW    -> redraw(renderer, app);
                case CLEAR_SCREEN -> renderer.clearScreen();
                case EXIT         -> { scanner.close(); return; }
                case NO_OP, ERROR -> { }
            }
        }
        scanner.close();
    }

    private static void redraw(ConsoleRenderer renderer, PixelTracerApp app) {
        renderer.clearScreen();
        renderer.drawAllLayers(app.getCurrentArea());
        renderer.print(app.getCurrentArea());
    }
}
