package gametetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {

	public static final int FALL = 25;
	public static final int TILE = 25;
	public static int WIDTH = TILE * 12;
	public static int HEIGHT = TILE * 24;
	public static int[][] MAP = new int[WIDTH / TILE][HEIGHT / TILE];
	private static Pane group = new Pane();
	private static FormBlock object;
	private static Scene scene = new Scene(group, WIDTH + 150, HEIGHT);
	public static int score = 0;
	private static int top = 0;
	private static boolean game = true;
	private static FormBlock nextObj = TetrisController.makeRect();
	private static int linesNo = 0;

	private static int levelNo = 1;
	private static Timer fall = new Timer();
	private static TimerTask task;
	private Stage gameStage; // Biến lưu cửa sổ game
	private Group nextBlockGroup = new Group(); // Nhóm để hiển thị khối tiếp theo
	private static boolean isPaused = false; // Trạng thái tạm dừng
	private boolean isGameOver = false; // Trạng thái game
	private Text scoreText = new Text("Score: 0");
	private Text levelText = new Text("Level: 1");
	private Text lineText = new Text("Lines: 0");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.gameStage = stage; // Lưu Stage vào biến toàn cục

		initializeGame(); // Gọi hàm khởi tạo game

		// Thiết lập Scene và hiển thị cửa sổ
		stage.setScene(scene);
		stage.setTitle("T E T R I S");
		stage.show();

		// Thiết lập logic rơi khối và kiểm tra trạng thái game
		Timer fall = new Timer();
		task = new TimerTask() {
			public void run() {
				Platform.runLater(() -> {
					if (!isPaused) { // Chỉ chạy khi game không bị tạm dừng
						if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0
								|| object.d.getY() == 0)
							top++;
						else
							top = 0;

						if (top == 2) {
							// GAME OVER
							Text over = new Text("GAME OVER");
							over.setFill(Color.RED);
							over.setStyle("-fx-font: 70 arial;");
							over.setY(250);
							over.setX(10);
							group.getChildren().add(over);
							game = false;
						}
						// Đóng cửa sổ sau khi Game Over
						if (top == 15) {
							gameStage.close();
						}

						if (game) {
							fallDown(object);
							scoreText.setText("Score: " + Integer.toString(score));
							lineText.setText("Lines: " + Integer.toString(linesNo));
							levelText.setText("Level: " + levelNo);

							// Tăng cấp độ mỗi 10 dòng
							if (linesNo > levelNo * 10) {
								levelNo++;
								adjustFallSpeed();
							}
						}
					}
				});
			}
		};

		fall.schedule(task, 0, 300); // Lên lịch thực hiện logic rơi

		// Sự kiện đóng cửa sổ game
		gameStage.setOnCloseRequest(event -> {
			isGameOver = true;
		});
	}

	public void initializeGame() {
		// Reset toàn bộ trạng thái game
		for (int[] a : MAP) {
			Arrays.fill(a, 0); 
		}
		group.getChildren().clear(); 
		nextBlockGroup.getChildren().clear(); 

		score = 0; 
		linesNo = 0; 
		levelNo = 1; 
		top = 0; 
		game = true; 
		isPaused = false; 
		isGameOver = false; 

		// Tạo lại giao diện ban đầu
		Line line = new Line(WIDTH, 0, WIDTH, HEIGHT);

		scoreText = new Text("Score: ");
		scoreText.setStyle("-fx-font: 20 arial;");
		scoreText.setY(200);
		scoreText.setX(WIDTH + 5);

		lineText = new Text("Lines: ");
		lineText.setStyle("-fx-font: 20 arial;");
		lineText.setY(250);
		lineText.setX(WIDTH + 5);
		lineText.setFill(Color.GREEN);

		levelText = new Text("Level: ");
		levelText.setStyle("-fx-font: 20 arial;");
		levelText.setY(300);
		levelText.setX(WIDTH + 5);
		levelText.setFill(Color.BLUE);

		Text nextText = new Text("Next: ");
		nextText.setStyle("-fx-font: 20 arial;");
		nextText.setY(50);
		nextText.setX(WIDTH + 5);

		// Thêm các thành phần vào group
		group.getChildren().addAll(line, scoreText, lineText, levelText, nextText, nextBlockGroup);

		// Tạo khối đầu tiên
		FormBlock a = nextObj;
		group.getChildren().addAll(a.a, a.b, a.c, a.d);
		moveOnKeyPress(a); // Thêm sự kiện di chuyển
		object = a; // Lưu khối hiện tại
		nextObj = TetrisController.makeRect(); // Tạo khối tiếp theo
		updateNextBlockDisplay(); // Cập nhật hiển thị "Next"
	}

	private void moveOnKeyPress(FormBlock a) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
					case RIGHT:
						TetrisController.fallRight(a);
						break;
					case DOWN:
						fallDown(a);
						score++;
						break;
					case LEFT:
						TetrisController.fallLeft(a);
						break;
					case UP:
						fallTurn(a);
						break;
					case SPACE:
						togglePause();
						break;
				}
			}
		});
	}

	private void fallTurn(FormBlock form) {
		int f = form.form;
		Rectangle a = form.a;
		Rectangle b = form.b;
		Rectangle c = form.c;
		Rectangle d = form.d;
		switch (form.getName()) {
			case "j":
				if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
					fallRight(form.a);
					fallDown(form.a);
					fallDown(form.c);
					fallLeft(form.c);
					fallDown(form.d);
					fallDown(form.d);
					fallLeft(form.d);
					fallLeft(form.d);
					form.changeForm();
					break;
				}
				if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
					fallDown(form.a);
					fallLeft(form.a);
					fallLeft(form.c);
					fallUp(form.c);
					fallLeft(form.d);
					fallLeft(form.d);
					fallUp(form.d);
					fallUp(form.d);
					form.changeForm();
					break;
				}
				if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
					fallLeft(form.a);
					fallUp(form.a);
					fallUp(form.c);
					fallRight(form.c);
					fallUp(form.d);
					fallUp(form.d);
					fallRight(form.d);
					fallRight(form.d);
					form.changeForm();
					break;
				}
				if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
					fallUp(form.a);
					fallRight(form.a);
					fallRight(form.c);
					fallDown(form.c);
					fallRight(form.d);
					fallRight(form.d);
					fallDown(form.d);
					fallDown(form.d);
					form.changeForm();
					break;
				}
				break;
			case "l":
				if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
					fallRight(form.a);
					fallDown(form.a);
					fallUp(form.c);
					fallRight(form.c);
					fallUp(form.b);
					fallUp(form.b);
					fallRight(form.b);
					fallRight(form.b);
					form.changeForm();
					break;
				}
				if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
					fallDown(form.a);
					fallLeft(form.a);
					fallRight(form.b);
					fallRight(form.b);
					fallDown(form.b);
					fallDown(form.b);
					fallRight(form.c);
					fallDown(form.c);
					form.changeForm();
					break;
				}
				if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
					fallLeft(form.a);
					fallUp(form.a);
					fallDown(form.c);
					fallLeft(form.c);
					fallDown(form.b);
					fallDown(form.b);
					fallLeft(form.b);
					fallLeft(form.b);
					form.changeForm();
					break;
				}
				if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
					fallUp(form.a);
					fallRight(form.a);
					fallLeft(form.b);
					fallLeft(form.b);
					fallUp(form.b);
					fallUp(form.b);
					fallLeft(form.c);
					fallUp(form.c);
					form.changeForm();
					break;
				}
				break;
			case "o":
				break;
			case "s":
				if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
					fallDown(form.a);
					fallLeft(form.a);
					fallLeft(form.c);
					fallUp(form.c);
					fallUp(form.d);
					fallUp(form.d);
					form.changeForm();
					break;
				}
				if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
					fallUp(form.a);
					fallRight(form.a);
					fallRight(form.c);
					fallDown(form.c);
					fallDown(form.d);
					fallDown(form.d);
					form.changeForm();
					break;
				}
				if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
					fallDown(form.a);
					fallLeft(form.a);
					fallLeft(form.c);
					fallUp(form.c);
					fallUp(form.d);
					fallUp(form.d);
					form.changeForm();
					break;
				}
				if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
					fallUp(form.a);
					fallRight(form.a);
					fallRight(form.c);
					fallDown(form.c);
					fallDown(form.d);
					fallDown(form.d);
					form.changeForm();
					break;
				}
				break;
			case "t":
				if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
					fallUp(form.a);
					fallRight(form.a);
					fallDown(form.d);
					fallLeft(form.d);
					fallLeft(form.c);
					fallUp(form.c);
					form.changeForm();
					break;
				}
				if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
					fallRight(form.a);
					fallDown(form.a);
					fallLeft(form.d);
					fallUp(form.d);
					fallUp(form.c);
					fallRight(form.c);
					form.changeForm();
					break;
				}
				if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
					fallDown(form.a);
					fallLeft(form.a);
					fallUp(form.d);
					fallRight(form.d);
					fallRight(form.c);
					fallDown(form.c);
					form.changeForm();
					break;
				}
				if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
					fallLeft(form.a);
					fallUp(form.a);
					fallRight(form.d);
					fallDown(form.d);
					fallDown(form.c);
					fallLeft(form.c);
					form.changeForm();
					break;
				}
				break;
			case "z":
				if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
					fallUp(form.b);
					fallRight(form.b);
					fallLeft(form.c);
					fallUp(form.c);
					fallLeft(form.d);
					fallLeft(form.d);
					form.changeForm();
					break;
				}
				if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
					fallDown(form.b);
					fallLeft(form.b);
					fallRight(form.c);
					fallDown(form.c);
					fallRight(form.d);
					fallRight(form.d);
					form.changeForm();
					break;
				}
				if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
					fallUp(form.b);
					fallRight(form.b);
					fallLeft(form.c);
					fallUp(form.c);
					fallLeft(form.d);
					fallLeft(form.d);
					form.changeForm();
					break;
				}
				if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
					fallDown(form.b);
					fallLeft(form.b);
					fallRight(form.c);
					fallDown(form.c);
					fallRight(form.d);
					fallRight(form.d);
					form.changeForm();
					break;
				}
				break;
			case "i":
				if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
					fallUp(form.a);
					fallUp(form.a);
					fallRight(form.a);
					fallRight(form.a);
					fallUp(form.b);
					fallRight(form.b);
					fallDown(form.d);
					fallLeft(form.d);
					form.changeForm();
					break;
				}
				if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
					fallDown(form.a);
					fallDown(form.a);
					fallLeft(form.a);
					fallLeft(form.a);
					fallDown(form.b);
					fallLeft(form.b);
					fallUp(form.d);
					fallRight(form.d);
					form.changeForm();
					break;
				}
				if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
					fallUp(form.a);
					fallUp(form.a);
					fallRight(form.a);
					fallRight(form.a);
					fallUp(form.b);
					fallRight(form.b);
					fallDown(form.d);
					fallLeft(form.d);
					form.changeForm();
					break;
				}
				if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
					fallDown(form.a);
					fallDown(form.a);
					fallLeft(form.a);
					fallLeft(form.a);
					fallDown(form.b);
					fallLeft(form.b);
					fallUp(form.d);
					fallRight(form.d);
					form.changeForm();
					break;
				}
				break;
		}
	}

	private void RemoveRows(Pane pane) {
		ArrayList<Node> rects = new ArrayList<Node>();
		ArrayList<Integer> lines = new ArrayList<Integer>();
		ArrayList<Node> newrects = new ArrayList<Node>();
		int full = 0;
		for (int i = 0; i < MAP[0].length; i++) {
			for (int j = 0; j < MAP.length; j++) {
				if (MAP[j][i] == 1)
					full++;
			}
			if (full == MAP.length)
				lines.add(i);
			// lines.add(i + lines.TILE());
			full = 0;
		}
		if (lines.size() > 0)
			do {
				for (Node node : pane.getChildren()) {
					if (node instanceof Rectangle)
						rects.add(node);
				}
				score += 50;
				linesNo++;

				for (Node node : rects) {
					Rectangle a = (Rectangle) node;
					if (a.getY() == lines.get(0) * TILE) {
						MAP[(int) a.getX() / TILE][(int) a.getY() / TILE] = 0;
						pane.getChildren().remove(node);
					} else
						newrects.add(node);
				}

				for (Node node : newrects) {
					Rectangle a = (Rectangle) node;
					if (a.getY() < lines.get(0) * TILE) {
						MAP[(int) a.getX() / TILE][(int) a.getY() / TILE] = 0;
						a.setY(a.getY() + TILE);
					}
				}
				lines.remove(0);
				rects.clear();
				newrects.clear();
				for (Node node : pane.getChildren()) {
					if (node instanceof Rectangle)
						rects.add(node);
				}
				for (Node node : rects) {
					Rectangle a = (Rectangle) node;
					try {
						MAP[(int) a.getX() / TILE][(int) a.getY() / TILE] = 1;
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}
				rects.clear();
			} while (lines.size() > 0);
	}

	private void handleKey(KeyCode code, FormBlock a) {
		switch (code) {
			case RIGHT:
				TetrisController.fallRight(a);
				break;
			case DOWN:
				fallDown(a);
				score++;
				break;
			case LEFT:
				TetrisController.fallLeft(a);
				break;
			case UP:
				fallTurn(a);
				break;
		}
	}

	private void startGameLoop() {
		fall = new Timer();
		task = new TimerTask() {
			public void run() {
				Platform.runLater(() -> {
					if (!isPaused && game) {
						if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0
								|| object.d.getY() == 0)
							top++;
						else
							top = 0;

						if (top == 2) {
							// GAME OVER
							Text over = new Text("GAME OVER");
							over.setFill(Color.RED);
							over.setStyle("-fx-font: 70 arial;");
							over.setY(250);
							over.setX(10);
							group.getChildren().add(over);
							game = false;
						}

						// Exit game
						if (top == 15) {
							gameStage.close();
						}

						if (game) {
							fallDown(object);
						}
					}
				});
			}
		};
		fall.schedule(task, 0, 300); // Điều chỉnh tốc độ theo nhu cầu
	}

	private void togglePause() {
		isPaused = !isPaused; // Chuyển trạng thái tạm dừng

		if (isPaused) {
			fall.cancel(); // Dừng timer
			Text pauseText = new Text("PAUSED");
			pauseText.setFill(Color.YELLOW);
			pauseText.setStyle("-fx-font: 40 arial;");
			pauseText.setX(WIDTH / 2.0 - 70);
			pauseText.setY(HEIGHT / 2.0);
			pauseText.setId("pauseText"); // Đặt ID để dễ xoá sau
			group.getChildren().add(pauseText);
		} else {
			group.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("pauseText")); // Xóa text
																											// "PAUSED"
			startGameLoop(); // Tiếp tục timer
		}
	}

	// Phương thức điều chỉnh tốc độ rơi
	private void adjustFallSpeed() {
		fall.cancel();
		fall = new Timer();
		int newInterval = Math.max(50, 300 - levelNo * 25); // Giảm thời gian rơi
		fall.schedule(task, 0, newInterval);
	}

	private void updateNextBlockDisplay() {
		// Xóa khối cũ khỏi `nextBlockGroup`
		nextBlockGroup.getChildren().clear();

		// Lấy tọa độ bắt đầu để vẽ khối tiếp theo
		double startX = 218;
		double startY = 90;

		// Thêm khối tiếp theo vào `nextBlockGroup`
		for (Rectangle rect : Arrays.asList(nextObj.a, nextObj.b, nextObj.c, nextObj.d)) {
			Rectangle clone = new Rectangle(rect.getWidth(), rect.getHeight(), rect.getFill());
			clone.setX(startX + rect.getX());
			clone.setY(startY + rect.getY());
			nextBlockGroup.getChildren().add(clone);
		}
	}

	private void fallDown(Rectangle rect) {
		if (rect.getY() + FALL < HEIGHT)
			rect.setY(rect.getY() + FALL);

	}

	private void fallRight(Rectangle rect) {
		if (rect.getX() + FALL <= WIDTH - TILE)
			rect.setX(rect.getX() + FALL);
	}

	private void fallLeft(Rectangle rect) {
		if (rect.getX() - FALL >= 0)
			rect.setX(rect.getX() - FALL);
	}

	private void fallUp(Rectangle rect) {
		if (rect.getY() - FALL > 0)
			rect.setY(rect.getY() - FALL);
	}

	private void fallDown(FormBlock form) {
		if (form.a.getY() == HEIGHT - TILE || form.b.getY() == HEIGHT - TILE || form.c.getY() == HEIGHT - TILE
				|| form.d.getY() == HEIGHT - TILE || moveA(form) || moveB(form) || moveC(form) || moveD(form)) {
			MAP[(int) form.a.getX() / TILE][(int) form.a.getY() / TILE] = 1;
			MAP[(int) form.b.getX() / TILE][(int) form.b.getY() / TILE] = 1;
			MAP[(int) form.c.getX() / TILE][(int) form.c.getY() / TILE] = 1;
			MAP[(int) form.d.getX() / TILE][(int) form.d.getY() / TILE] = 1;
			RemoveRows(group);

			FormBlock a = nextObj;
			nextObj = TetrisController.makeRect();
			object = a;
			group.getChildren().addAll(a.a, a.b, a.c, a.d);
			moveOnKeyPress(a);

			// Cập nhật và vẽ lại khối tiếp theo
			updateNextBlockDisplay();
		}

		if (form.a.getY() + FALL < HEIGHT && form.b.getY() + FALL < HEIGHT && form.c.getY() + FALL < HEIGHT
				&& form.d.getY() + FALL < HEIGHT) {
			int movea = MAP[(int) form.a.getX() / TILE][((int) form.a.getY() / TILE) + 1];
			int moveb = MAP[(int) form.b.getX() / TILE][((int) form.b.getY() / TILE) + 1];
			int movec = MAP[(int) form.c.getX() / TILE][((int) form.c.getY() / TILE) + 1];
			int moved = MAP[(int) form.d.getX() / TILE][((int) form.d.getY() / TILE) + 1];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				form.a.setY(form.a.getY() + FALL);
				form.b.setY(form.b.getY() + FALL);
				form.c.setY(form.c.getY() + FALL);
				form.d.setY(form.d.getY() + FALL);
			}
		}
	}

	private boolean moveA(FormBlock form) {
		return (MAP[(int) form.a.getX() / TILE][((int) form.a.getY() / TILE) + 1] == 1);
	}

	private boolean moveB(FormBlock form) {
		return (MAP[(int) form.b.getX() / TILE][((int) form.b.getY() / TILE) + 1] == 1);
	}

	private boolean moveC(FormBlock form) {
		return (MAP[(int) form.c.getX() / TILE][((int) form.c.getY() / TILE) + 1] == 1);
	}

	private boolean moveD(FormBlock form) {
		return (MAP[(int) form.d.getX() / TILE][((int) form.d.getY() / TILE) + 1] == 1);
	}

	private boolean cB(Rectangle rect, int x, int y) {
		boolean xb = false;
		boolean yb = false;
		if (x >= 0)
			xb = rect.getX() + x * FALL <= WIDTH - TILE;
		if (x < 0)
			xb = rect.getX() + x * FALL >= 0;
		if (y >= 0)
			yb = rect.getY() - y * FALL > 0;
		if (y < 0)
			yb = rect.getY() + y * FALL < HEIGHT;
		return xb && yb && MAP[((int) rect.getX() / TILE) + x][((int) rect.getY() / TILE) - y] == 0;
	}

	// Hàm reset game, chuẩn bị cho một lượt chơi mới
	private void resetGame() {
		isGameOver = false; // Đặt lại trạng thái game
	}

	public boolean isGameOver() {
		return isGameOver;
	}
}
