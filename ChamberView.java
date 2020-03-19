import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class ChamberView extends JPanel {
	private Game game;
	private Maze maze;
	private Position playerPos, nextRoomPos;
	private int playerDirection; // N0, E1, S2, W3
	private Vector cameraPos, screenPlaneRelPos;
	private Room currentRoom, nextRoom;
	private ArrayList<Plane> planeList;
	private double theta, phi;
	private java.util.Timer timer = new java.util.Timer();
	private int animationTimer;
	private int animationType; //0 default, 1 rotate right, 2 rotate left, 3 forwards, 4 upwards, 5 downwards
	public double doorAngleN, doorAngleS, doorAngleE, doorAngleW, doorAngleU, doorAngleD;
	public Color[] doorColorArray, nextRoomDoorColorArray;
	private boolean pressed=false, menuOn=false, timerrun=true;
	private int phicount, speed;
	private ChamberLayers chamberLayers;
	private MovementListener movementListener;

	private class MovementListener implements ActionListener, KeyListener, MouseListener {
		private static final boolean left=true;
		private static final boolean right=false;
		private static final int forward=0;
		private static final int down=1;
		private static final int up=2;
		private Player player;
		
		public MovementListener() {
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("up")) {
				moveUp();
			} else if (e.getActionCommand().equals("left")) {
				turnLeft();
			} else if (e.getActionCommand().equals("forward")) {
				moveForward();
			} else if (e.getActionCommand().equals("right")) {
				turnRight();
			} else if (e.getActionCommand().equals("down")) {
				moveDown();
			} else if (e.getActionCommand().equals("Menu")) {
				menuOn=!menuOn;
			}
		}
		public void keyTyped (KeyEvent event) {}
		public void keyPressed (KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_W) {
				phicount++;
				phi = phicount*Math.PI/40;
				timerOne();
				//phi += Math.PI/2/30;
			} else if (event.getKeyCode() == KeyEvent.VK_S) {
				phicount--;
				phi = phicount*Math.PI/40;
				timerOne();
				//phi -= Math.PI/2/30;
			} else if (event.getKeyCode() == KeyEvent.VK_A) {
				theta -= Math.PI/(2*40);
				timerOne();
				//phi += Math.PI/2/30;
			} else if (event.getKeyCode() == KeyEvent.VK_D) {
				theta += Math.PI/(2*40);
				timerOne();
				//phi -= Math.PI/2/30;
			}
		}
		public void keyReleased (KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_TAB) {
				game.goToMapView();
			}
			if (event.getKeyCode() == KeyEvent.VK_1) { speed+=5; System.out.println(speed);}
			if (event.getKeyCode() == KeyEvent.VK_2) { speed-=5; System.out.println(speed);}
			if (event.getKeyCode() == KeyEvent.VK_4) { timerrun=!timerrun; if (!timerrun) timerOne();}
			if (event.getKeyCode() == KeyEvent.VK_3) { timerOne();}


			//cameraPos = cameraPos.plus(new Position(0, -5, 0));
			if (event.getKeyCode() == KeyEvent.VK_UP && new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1).isValid(maze.getSize())
					&& maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.up)) {
				//cameraPos = cameraPos.plus(screenPlaneRelPos.scale(0.25));

				//					playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
				//					Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
				//					cameraPos = roomVector.clone().plus(new Vector(50,50,50));

				if (!pressed) {
					pressed = true;
					animationTimer = 0;
					animationType = 4;
					nextRoomPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
					nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
				}

			} else if (event.getKeyCode() == KeyEvent.VK_DOWN && new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1).isValid(maze.getSize())
					&& maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.down)) {
				//cameraPos = cameraPos.plus(screenPlaneRelPos.scale(-0.25));

				//					playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
				//					Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
				//					cameraPos = roomVector.clone().plus(new Vector(50,50,50));

				if (!pressed) {
					pressed = true;
					animationTimer = 0;
					animationType = 5;
					nextRoomPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
					nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
				}

			} else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (!pressed) {
					pressed=true;
					//theta += (4*Math.PI)/(2*90);
					//theta += Math.PI/2;
					playerDirection = (playerDirection + 1) % 4;
					//theta = playerDirection*Math.PI/2;
					animationTimer = 0;
					animationType = 1;
				}
			} else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
				if (!pressed) {
					pressed=true;
					//theta -= (4*Math.PI)/(2*90);
					//theta -= Math.PI/2;
					playerDirection = (playerDirection + 3) % 4;
					//theta = playerDirection*Math.PI/2;
					animationTimer = 0;
					animationType = 2;
				}
			} else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
				if (!pressed) {
					pressed=true;
					System.out.println(currentRoom);
					boolean shouldMove = false;
					if (playerDirection == 0 && new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ()).isValid(maze.getSize()) && 
							maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.north)) {
						//playerPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
						nextRoomPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
						nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
						shouldMove = true;
					} else if (playerDirection == 2 && new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ()).isValid(maze.getSize()) &&
							maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.south)) {
						//playerPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
						nextRoomPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
						nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
						shouldMove = true;
					} else if (playerDirection == 1 && new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ()).isValid(maze.getSize()) &&
							maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.east)) {
						//playerPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
						nextRoomPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
						nextRoom = maze.getRoom(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
						shouldMove = true;
					} else if (playerDirection == 3 && new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ()).isValid(maze.getSize()) &&
							maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.west)) {
						//playerPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
						nextRoomPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
						nextRoom = maze.getRoom(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
						shouldMove = true;
					} else {
						pressed=false;
					}

					Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
					cameraPos = roomVector.clone().plus(new Vector(50,50,50));
					System.out.println("playerDirection: " + playerDirection);
					System.out.println("playerPos: " + playerPos);
					if (shouldMove) {
						animationTimer = 0;
						animationType = 3;
					}
				}
			} else if (event.getKeyCode() == KeyEvent.VK_O) {
				doorAngleN += Math.PI/2 /10;
			} else if (event.getKeyCode() == KeyEvent.VK_L) {
				doorAngleS += Math.PI/2 /10;
			} else if (event.getKeyCode() == KeyEvent.VK_P) {
				doorAngleE += Math.PI/2 /10;
			} else if (event.getKeyCode() == KeyEvent.VK_I) {
				doorAngleW += Math.PI/2 /10;
			}
			//				screenPlaneRelPos = new Vector(20*Math.cos(phi)*Math.sin(theta), 20*Math.cos(phi)*Math.cos(theta), 20*Math.sin(phi));
			//				panel.repaint();
			//				System.out.println("--------------------");
			//				System.out.println("cameraPos: " + cameraPos);
			//				System.out.println("screenPlaneRelPos: " + screenPlaneRelPos);
			if (timerrun)
				timer();
			}

		public void move(int direction) {
			player.moveForward();
			int[] position=player.getPosition();
			if (position[0]==0&&position[1]==0&&position[2]==0) {
				//game.win();
			}
		}
		
		public void rotate(boolean leftOrRight) {
			if (leftOrRight) {
				player.turnLeft();
			} else {
				player.turnRight();
			}
		}
		
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	public ChamberView (Game game, Maze maze, ChamberLayers chamberLayers) {
		this.chamberLayers = chamberLayers;
		this.setBackground(Color.WHITE);
		this.maze = maze;
		doorColorArray = new Color[6];
		nextRoomDoorColorArray = new Color[6];
		animationTimer = -1;
		animationType = 0;
		speed=20;
		playerPos = new Position(maze.getSize()-1,maze.getSize()-1,maze.getSize()-1);
		System.out.println("Maze size: " + maze.getSize());
		currentRoom = maze.getRoom(playerPos);
		nextRoom = null;
		theta = 0;
		phi = 0;
		phicount = 0;
		cameraPos = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ()).clone().plus(new Vector(50,50,50));
		screenPlaneRelPos = new Vector(0, 13, 0);
		setUpRoom();

		this.setFocusable(true);
		ChamberView panel = this;
		movementListener=new MovementListener();
		game.getFrame().addKeyListener(movementListener);
		//System.out.println("hi1");
//		game.getFrame().addKeyListener(new KeyListener () {
//			public void keyTyped (KeyEvent event) {}
//			public void keyPressed (KeyEvent event) {
//				//System.out.println("KEY PRESSED");
//				//cameraPos = cameraPos.plus(new Position(0, -5, 0));
//				if (event.getKeyCode() == KeyEvent.VK_UP && new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1).isValid(maze.getSize())
//						&& maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.up)) {
//					//cameraPos = cameraPos.plus(screenPlaneRelPos.scale(0.25));
//					
////					playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
////					Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
////					cameraPos = roomVector.clone().plus(new Vector(50,50,50));
//					
//					if (!pressed) {
//						pressed = true;
//						animationTimer = 0;
//						animationType = 4;
//						nextRoomPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
//						nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
//					}
//					
//				} else if (event.getKeyCode() == KeyEvent.VK_DOWN && new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1).isValid(maze.getSize())
//						&& maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.down)) {
//					//cameraPos = cameraPos.plus(screenPlaneRelPos.scale(-0.25));
//					
////					playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
////					Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
////					cameraPos = roomVector.clone().plus(new Vector(50,50,50));
//					
//					if (!pressed) {
//						pressed = true;
//						animationTimer = 0;
//						animationType = 5;
//						nextRoomPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
//						nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
//					}
//					
//				} else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
//					if (!pressed) {
//						pressed=true;
//					//theta += (4*Math.PI)/(2*90);
//					//theta += Math.PI/2;
//					playerDirection = (playerDirection + 1) % 4;
//					//theta = playerDirection*Math.PI/2;
//					animationTimer = 0;
//					animationType = 1;
//					}
//				} else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
//					if (!pressed) {
//						pressed=true;
//					//theta -= (4*Math.PI)/(2*90);
//					//theta -= Math.PI/2;
//					playerDirection = (playerDirection + 3) % 4;
//					//theta = playerDirection*Math.PI/2;
//					animationTimer = 0;
//					animationType = 2;
//					}
//				} else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
//					if (!pressed) {
//						pressed=true;
//						//System.out.println(currentRoom);
//						boolean shouldMove = false;
//						if (playerDirection == 0 && new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ()).isValid(maze.getSize()) && 
//								maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.north)) {
//							//playerPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
//							nextRoomPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
//							nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
//							shouldMove = true;
//						} else if (playerDirection == 2 && new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ()).isValid(maze.getSize()) &&
//								maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.south)) {
//							//playerPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
//							nextRoomPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
//							nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
//							shouldMove = true;
//						} else if (playerDirection == 1 && new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ()).isValid(maze.getSize()) &&
//								maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.east)) {
//							//playerPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
//							nextRoomPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
//							nextRoom = maze.getRoom(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
//							shouldMove = true;
//						} else if (playerDirection == 3 && new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ()).isValid(maze.getSize()) &&
//								maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.west)) {
//							//playerPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
//							nextRoomPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
//							nextRoom = maze.getRoom(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
//							shouldMove = true;
//						} else {
//							pressed=false;
//						}
//
//						Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
//						cameraPos = roomVector.clone().plus(new Vector(50,50,50));
//						//System.out.println("playerDirection: " + playerDirection);
//						//System.out.println("playerPos: " + playerPos);
//						if (shouldMove) {
//							animationTimer = 0;
//							animationType = 3;
//						}
//					}
//				} else if (event.getKeyCode() == KeyEvent.VK_W) {
//					phicount++;
//					phi = phicount*Math.PI/40;
//					//phi += Math.PI/2/30;
//				} else if (event.getKeyCode() == KeyEvent.VK_S) {
//					phicount--;
//					phi = phicount*Math.PI/40;
//					//phi -= Math.PI/2/30;
//				} else if (event.getKeyCode() == KeyEvent.VK_O) {
//					doorAngleN += Math.PI/2 /10;
//				} else if (event.getKeyCode() == KeyEvent.VK_L) {
//					doorAngleS += Math.PI/2 /10;
//				} else if (event.getKeyCode() == KeyEvent.VK_P) {
//					doorAngleE += Math.PI/2 /10;
//				} else if (event.getKeyCode() == KeyEvent.VK_I) {
//					doorAngleW += Math.PI/2 /10;
//				}
////				screenPlaneRelPos = new Vector(20*Math.cos(phi)*Math.sin(theta), 20*Math.cos(phi)*Math.cos(theta), 20*Math.sin(phi));
////				panel.repaint();
////				System.out.println("--------------------");
////				System.out.println("cameraPos: " + cameraPos);
////				System.out.println("screenPlaneRelPos: " + screenPlaneRelPos);
//			}
//			public void keyReleased (KeyEvent event) {}
//		});

//		timer = new java.util.Timer();
//		timer.scheduleAtFixedRate(new TimerTask () {
//			public void run () {
//				setUpRoom();
//				if (animationTimer == 41 && (animationType != 4 && animationType != 5)) {
//					animationTimer = -1;
//					pressed=false;
//					if (animationType == 3) {
//						if (playerDirection == 0)
//							playerPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
//						else if (playerDirection == 1)
//							playerPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
//						else if (playerDirection == 2)
//							playerPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
//						else
//							playerPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
//					}
//					animationType = 0;
//					doorAngleN = doorAngleE = doorAngleS = doorAngleW = doorAngleU = doorAngleD = 0;
//					nextRoom = null;
//				} else if (animationTimer == 80 && (animationType == 4 || animationType == 5)) {
//					animationTimer = -1;
//					pressed=false;
//					if (animationType == 4) {
//						playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
//					} else if (animationType == 5) {
//						playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
//					}
//					animationType = 0;
//					doorAngleN = doorAngleE = doorAngleS = doorAngleW = doorAngleU = doorAngleD = 0;
//					nextRoom = null;
//				}
//				if (animationTimer == -1) {
//					theta = playerDirection*Math.PI/2;
//					phicount = 0;
//					phi = 0;
//				}else if (animationType == 1) {
//					animationTimer++;
//					theta += Math.PI/(2*40);
//				} else if (animationType == 2) {
//					animationTimer++;
//					theta -= Math.PI/(2*40);
//				} else if (animationType == 3) {
//					animationTimer++;
//					cameraPos = cameraPos.plus(screenPlaneRelPos.scale(100/screenPlaneRelPos.magnitude()/40));
//					if (playerDirection == 0)
//						doorAngleN = Math.min(animationTimer, 20)*Math.PI/2/20;
//					else if (playerDirection == 1)
//						doorAngleE = Math.min(animationTimer, 20)*Math.PI/2/20;
//					else if (playerDirection == 2)
//						doorAngleS = Math.min(animationTimer, 20)*Math.PI/2/20;
//					else
//						doorAngleW = Math.min(animationTimer, 20)*Math.PI/2/20;
//				} else if (animationType == 4) {
//					if (animationTimer < 20) {
//						phicount++;
//						phi = phicount*Math.PI/40;
//					} else if (animationTimer < 60) {
//						cameraPos = cameraPos.plus(new Vector(0,0,(double) 100/40));
//					} else {
//						phicount--;
//						phi = phicount*Math.PI/40;
//					}
//					if (animationTimer < 40) {
//						doorAngleU += Math.PI /40;
//					}
//					animationTimer++;
//				} else if (animationType == 5) {
//					if (animationTimer < 20) {
//						phicount--;
//						phi = phicount*Math.PI/40;
//					} else if (animationTimer < 60) {
//						cameraPos = cameraPos.plus(new Vector(0,0,(double) -100/40));
//					} else {
//						phicount++;
//						phi = phicount*Math.PI/40;
//					}
//					if (animationTimer < 40) {
//						doorAngleD += Math.PI /40;
//					}
//					animationTimer++;
//				}
//				//System.out.println(phicount);
//				screenPlaneRelPos = new Vector(15*Math.cos(phi)*Math.sin(theta), 15*Math.cos(phi)*Math.cos(theta), 15*Math.sin(phi));
//				panel.repaint();
//			}
//		}, 0, 20);
	}
	
	private void timerOne() {
		timer.cancel();
		timer = new java.util.Timer();
		timer.schedule(new TimerTask () {
			public void run () {
				setUpRoom();
				if (animationTimer == 41 && (animationType != 4 && animationType != 5)) {
					animationTimer = -1;
					pressed=false;
					if (animationType == 3) {
						if (playerDirection == 0)
							playerPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
						else if (playerDirection == 1)
							playerPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
						else if (playerDirection == 2)
							playerPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
						else
							playerPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
					}
					animationType = 0;
					doorAngleN = doorAngleE = doorAngleS = doorAngleW = doorAngleU = doorAngleD = 0;
					nextRoom = null;
				} else if (animationTimer == 80 && (animationType == 4 || animationType == 5)) {
					animationTimer = -1;
					pressed=false;
					if (animationType == 4) {
						playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
					} else if (animationType == 5) {
						playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
					}
					animationType = 0;
					doorAngleN = doorAngleE = doorAngleS = doorAngleW = doorAngleU = doorAngleD = 0;
					nextRoom = null;
				}
				if (animationTimer == -1) {
//					theta = playerDirection*Math.PI/2;
//					phicount = 0;
//					phi = 0;
				}else if (animationType == 1) {
					animationTimer++;
					theta += Math.PI/(2*40);
				} else if (animationType == 2) {
					animationTimer++;
					theta -= Math.PI/(2*40);
				} else if (animationType == 3) {
					animationTimer++;
					cameraPos = cameraPos.plus(screenPlaneRelPos.scale(100/screenPlaneRelPos.magnitude()/40));
					if (playerDirection == 0)
						doorAngleN = Math.min(animationTimer, 20)*Math.PI/2/20;
					else if (playerDirection == 1)
						doorAngleE = Math.min(animationTimer, 20)*Math.PI/2/20;
					else if (playerDirection == 2)
						doorAngleS = Math.min(animationTimer, 20)*Math.PI/2/20;
					else
						doorAngleW = Math.min(animationTimer, 20)*Math.PI/2/20;
				} else if (animationType == 4) {
					if (animationTimer < 20) {
						phicount++;
						phi = phicount*Math.PI/40;
					} else if (animationTimer < 60) {
						cameraPos = cameraPos.plus(new Vector(0,0,(double) 100/40));
					} else {
						phicount--;
						phi = phicount*Math.PI/40;
					}
					if (animationTimer < 40) {
						doorAngleU += Math.PI /40;
					}
					animationTimer++;
				} else if (animationType == 5) {
					if (animationTimer < 20) {
						phicount--;
						phi = phicount*Math.PI/40;
					} else if (animationTimer < 60) {
						cameraPos = cameraPos.plus(new Vector(0,0,(double) -100/40));
					} else {
						phicount++;
						phi = phicount*Math.PI/40;
					}
					if (animationTimer < 40) {
						doorAngleD += Math.PI /40;
					}
					animationTimer++;
				}
				//System.out.println(phicount);
				screenPlaneRelPos = new Vector(15*Math.cos(phi)*Math.sin(theta), 15*Math.cos(phi)*Math.cos(theta), 15*Math.sin(phi));
				System.out.println(phicount);
				ChamberView.this.repaint();
			}
			
		}, 0);
	}
	
	public void moveForward() {
		if (!pressed) {
			pressed=true;
			System.out.println(currentRoom);
			boolean shouldMove = false;
			if (playerDirection == 0 && new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ()).isValid(maze.getSize()) && 
					maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.north)) {
				//playerPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
				nextRoomPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
				nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
				shouldMove = true;
			} else if (playerDirection == 2 && new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ()).isValid(maze.getSize()) &&
					maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.south)) {
				//playerPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
				nextRoomPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
				nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
				shouldMove = true;
			} else if (playerDirection == 1 && new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ()).isValid(maze.getSize()) &&
					maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.east)) {
				//playerPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
				nextRoomPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
				nextRoom = maze.getRoom(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
				shouldMove = true;
			} else if (playerDirection == 3 && new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ()).isValid(maze.getSize()) &&
					maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.west)) {
				//playerPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
				nextRoomPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
				nextRoom = maze.getRoom(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
				shouldMove = true;
			} else {
				pressed=false;
			}

			Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
			cameraPos = roomVector.clone().plus(new Vector(50,50,50));
			System.out.println("playerDirection: " + playerDirection);
			System.out.println("playerPos: " + playerPos);
			if (shouldMove) {
				animationTimer = 0;
				animationType = 3;
			}
		}
	}

	
		public void moveUp() {
			if (new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1).isValid(maze.getSize())
					&& maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.up)) {
				//cameraPos = cameraPos.plus(screenPlaneRelPos.scale(0.25));

				//					playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
				//					Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
				//					cameraPos = roomVector.clone().plus(new Vector(50,50,50));

				if (!pressed) {
					pressed = true;
					animationTimer = 0;
					animationType = 4;
					nextRoomPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
					nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
				}
			}
		}

		public void moveDown() {
			if (new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1).isValid(maze.getSize())
					&& maze.getRoom(playerPos.getX(),  playerPos.getY(),  playerPos.getZ()).getDoor(Room.down)) {
				//cameraPos = cameraPos.plus(screenPlaneRelPos.scale(-0.25));

				//					playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
				//					Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());
				//					cameraPos = roomVector.clone().plus(new Vector(50,50,50));

				if (!pressed) {
					pressed = true;
					animationTimer = 0;
					animationType = 5;
					nextRoomPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
					nextRoom = maze.getRoom(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
				}

			}

		}
		
		public void turnLeft() {
			if (!pressed) {
				pressed=true;
				//theta -= (4*Math.PI)/(2*90);
				//theta -= Math.PI/2;
				playerDirection = (playerDirection + 3) % 4;
				//theta = playerDirection*Math.PI/2;
				animationTimer = 0;
				animationType = 2;
			}
		}

		public void turnRight() {
			if (!pressed) {
				pressed=true;
				//theta += (4*Math.PI)/(2*90);
				//theta += Math.PI/2;
				playerDirection = (playerDirection + 1) % 4;
				//theta = playerDirection*Math.PI/2;
				animationTimer = 0;
				animationType = 1;
			}
		}
		
		public void timer() {
			timer.cancel();
			timer = new java.util.Timer();
			timer.scheduleAtFixedRate(new TimerTask () {
				public void run () {
					setUpRoom();
					if (animationTimer == 41 && (animationType != 4 && animationType != 5)) {
						animationTimer = -1;
						pressed=false;
						if (animationType == 3) {
							if (playerDirection == 0)
								playerPos = new Position(playerPos.getX(), playerPos.getY()+1, playerPos.getZ());
							else if (playerDirection == 1)
								playerPos = new Position(playerPos.getX()+1, playerPos.getY(), playerPos.getZ());
							else if (playerDirection == 2)
								playerPos = new Position(playerPos.getX(), playerPos.getY()-1, playerPos.getZ());
							else
								playerPos = new Position(playerPos.getX()-1, playerPos.getY(), playerPos.getZ());
						}
						animationType = 0;
						doorAngleN = doorAngleE = doorAngleS = doorAngleW = doorAngleU = doorAngleD = 0;
						nextRoom = null;
					} else if (animationTimer == 80 && (animationType == 4 || animationType == 5)) {
						animationTimer = -1;
						pressed=false;
						if (animationType == 4) {
							playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()+1);
						} else if (animationType == 5) {
							playerPos = new Position(playerPos.getX(), playerPos.getY(), playerPos.getZ()-1);
						}
						animationType = 0;
						doorAngleN = doorAngleE = doorAngleS = doorAngleW = doorAngleU = doorAngleD = 0;
						nextRoom = null;
					}
					if (animationTimer == -1) {
//						theta = playerDirection*Math.PI/2;
//						phicount = 0;
//						phi = 0;
					}else if (animationType == 1) {
						animationTimer++;
						theta += Math.PI/(2*40);
					} else if (animationType == 2) {
						animationTimer++;
						theta -= Math.PI/(2*40);
					} else if (animationType == 3) {
						animationTimer++;
						cameraPos = cameraPos.plus(screenPlaneRelPos.scale(100/screenPlaneRelPos.magnitude()/40));
						if (playerDirection == 0)
							doorAngleN = Math.min(animationTimer, 20)*Math.PI/2/20;
						else if (playerDirection == 1)
							doorAngleE = Math.min(animationTimer, 20)*Math.PI/2/20;
						else if (playerDirection == 2)
							doorAngleS = Math.min(animationTimer, 20)*Math.PI/2/20;
						else
							doorAngleW = Math.min(animationTimer, 20)*Math.PI/2/20;
					} else if (animationType == 4) {
						if (animationTimer < 20) {
							phicount++;
							phi = phicount*Math.PI/40;
						} else if (animationTimer < 60) {
							cameraPos = cameraPos.plus(new Vector(0,0,(double) 100/40));
						} else {
							phicount--;
							phi = phicount*Math.PI/40;
						}
						if (animationTimer < 40) {
							doorAngleU += Math.PI /40;
						}
						animationTimer++;
					} else if (animationType == 5) {
						if (animationTimer < 20) {
							phicount--;
							phi = phicount*Math.PI/40;
						} else if (animationTimer < 60) {
							cameraPos = cameraPos.plus(new Vector(0,0,(double) -100/40));
						} else {
							phicount++;
							phi = phicount*Math.PI/40;
						}
						if (animationTimer < 40) {
							doorAngleD += Math.PI /40;
						}
						animationTimer++;
					}
					//System.out.println(phicount);
					screenPlaneRelPos = new Vector(15*Math.cos(phi)*Math.sin(theta), 15*Math.cos(phi)*Math.cos(theta), 15*Math.sin(phi));
					ChamberView.this.repaint();
				}
			}, 0, speed);
		}
	private void setUpRoom () {
		planeList = new ArrayList<Plane>();
		if (nextRoom != null) {
			Color nextRoomColor = nextRoom.getColor();
			Vector roomVector = new Vector(100*nextRoomPos.getX(), 100*nextRoomPos.getY(), 100*nextRoomPos.getZ());

			for (int i = 0; i < 6; i++)
				if (nextRoom.getDoor(i))
					nextRoomDoorColorArray[i] = Color.BLACK;
				else if (0 <= i && i <= 3)
					nextRoomDoorColorArray[i] = nextRoomColor;
				else
					nextRoomDoorColorArray[i] = Color.GRAY;
			
			if (animationType != 4 || animationTimer > 20) {
				for (int i=0;i<96;i+=5)
					for (int j=0;j<96;j+=5)
						if (!nextRoom.getDoor(Room.down)||i<21||i>69||j<21||j>69)
							planeList.add(new Plane(new Vector(j,i,0).plus(roomVector), new Vector(j+10,i,0).plus(roomVector), new Vector(j+10,i+10,0).plus(roomVector), new Vector(j,i+10,0).plus(roomVector), Color.GRAY));
				for (int i = 30; i < 61; i += 5)
					for (int j = 30; j < 61; j += 5)
						planeList.add(new Plane(new Vector(i,j,0).plus(roomVector), new Vector(i,j+10,0).plus(roomVector), 
								new Vector(i+10,j+10,0).plus(roomVector), new Vector(i+10,j,0).plus(roomVector), nextRoomDoorColorArray[5]));
			}
			if (animationType != 5 || animationTimer > 20) {
				for (int i=0;i<96;i+=5)
					for (int j=0;j<96;j+=5)
						if (!nextRoom.getDoor(Room.up)||i<21||i>69||j<21||j>69)
							planeList.add(new Plane(new Vector(j,i,100).plus(roomVector), new Vector(j+10,i,100).plus(roomVector), new Vector(j+10,i+10,100).plus(roomVector), new Vector(j,i+10,100).plus(roomVector), Color.GRAY));
				for (int i = 30; i < 61; i += 5)
					for (int j = 30; j < 61; j += 5)
						planeList.add(new Plane(new Vector(i,j,100).plus(roomVector), new Vector(i,j+10,100).plus(roomVector), 
								new Vector(i+10,j+10,100).plus(roomVector), new Vector(i+10,j,100).plus(roomVector), nextRoomDoorColorArray[4]));
			}
			if (playerDirection != Room.south || animationType == 4 || animationType == 5) {
				for (int i=0;i<96;i+=5)
					for (int j=0;j<96;j+=5)
						if (!nextRoom.getDoor(Room.north)||i>59||j<21||j>69)
							planeList.add(new Plane(new Vector(j,100,i).plus(roomVector), new Vector(j+10,100,i).plus(roomVector), new Vector(j+10,100,i+10).plus(roomVector), new Vector(j,100,i+10).plus(roomVector), nextRoomColor));
				for (int i = 30; i < 61; i += 5)
					for (int j = 0; j < 51; j += 5)
						planeList.add(new Plane(new Vector(i,100,j).plus(roomVector), new Vector(i+10,100,j).plus(roomVector), 
								new Vector(i+10, 100, j+10).plus(roomVector), new Vector(i,100,j+10).plus(roomVector), nextRoomDoorColorArray[0]));
			}
			if (playerDirection != Room.north || animationType == 4 || animationType == 5) {
				for (int i=0;i<96;i+=5)
					for (int j=0;j<96;j+=5)
						if (!nextRoom.getDoor(Room.south)||i>59||j<21||j>69)
							planeList.add(new Plane(new Vector(j,0,i).plus(roomVector), new Vector(j+10,0,i).plus(roomVector), new Vector(j+10,0,i+10).plus(roomVector), new Vector(j,0,i+10).plus(roomVector), nextRoomColor));
				for (int i = 30; i < 61; i += 5)
					for (int j = 0; j < 51; j += 5)
						planeList.add(new Plane(new Vector(i,0,j).plus(roomVector), new Vector(i+10,0,j).plus(roomVector), 
								new Vector(i+10, 0, j+10).plus(roomVector), new Vector(i,0,j+10).plus(roomVector), nextRoomDoorColorArray[2]));
			}
			if (playerDirection != Room.west || animationType == 4 || animationType == 5) {
				for (int i=0;i<96;i+=5)
					for (int j=0;j<96;j+=5)
						if (!nextRoom.getDoor(Room.east)||i>59||j<21||j>69)
							planeList.add(new Plane(new Vector(100,j,i).plus(roomVector), new Vector(100,j+10,i).plus(roomVector), new Vector(100,j+10,i+10).plus(roomVector), new Vector(100,j,i+10).plus(roomVector), nextRoomColor));
				for (int i = 30; i < 61; i += 5)
					for (int j = 0; j < 51; j += 5)
						planeList.add(new Plane(new Vector(100,i,0).plus(roomVector), new Vector(100,i+10,j).plus(roomVector), 
								new Vector(100,i+10, j+10).plus(roomVector), new Vector(100,i,j+10).plus(roomVector), nextRoomDoorColorArray[1]));
			}
			if (playerDirection != Room.east || animationType == 4 || animationType == 5) {
				for (int i=0;i<96;i+=5)
					for (int j=0;j<96;j+=5)
						if (!nextRoom.getDoor(Room.west)||i>59||j<21||j>69)
							planeList.add(new Plane(new Vector(0,j,i).plus(roomVector), new Vector(0,j+10,i).plus(roomVector), new Vector(0,j+10,i+10).plus(roomVector), new Vector(0,j,i+10).plus(roomVector), nextRoomColor));
				for (int i = 30; i < 61; i += 5)
					for (int j = 0; j < 51; j += 5)
						planeList.add(new Plane(new Vector(0,i,0).plus(roomVector), new Vector(0,i+10,j).plus(roomVector), 
								new Vector(0,i+10, j+10).plus(roomVector), new Vector(0,i,j+10).plus(roomVector), nextRoomDoorColorArray[3]));
			}


			planeList.add(new Plane(new Vector(1,99,0).plus(roomVector), new Vector(2,99,0).plus(roomVector), new Vector(1,101,107).plus(roomVector), new Vector(0,101,107).plus(roomVector), Color.BLACK));
			planeList.add(new Plane(new Vector(99,99,0).plus(roomVector), new Vector(100,99,0).plus(roomVector), new Vector(102,101,107).plus(roomVector), new Vector(101,101,107).plus(roomVector), Color.BLACK));
			planeList.add(new Plane(new Vector(0,0,0).plus(roomVector), new Vector(0,1,0).plus(roomVector), new Vector(0,1,105).plus(roomVector), new Vector(0,0,105).plus(roomVector), Color.BLACK));
			planeList.add(new Plane(new Vector(99,0,0).plus(roomVector), new Vector(99,1,0).plus(roomVector), new Vector(99,1,105).plus(roomVector), new Vector(99,0,105).plus(roomVector), Color.BLACK));
		}



		currentRoom = maze.getRoom(playerPos);
		Color roomColor = currentRoom.getColor();
		Vector roomVector = new Vector(100*playerPos.getX(), 100*playerPos.getY(), 100*playerPos.getZ());

		for (int i=0;i<96;i+=5)
			for (int j=0;j<96;j+=5)
				if (!currentRoom.getDoor(Room.down)||i<21||i>69||j<21||j>69)
					planeList.add(new Plane(new Vector(j,i,0).plus(roomVector), new Vector(j+10,i,0).plus(roomVector), new Vector(j+10,i+10,0).plus(roomVector), new Vector(j,i+10,0).plus(roomVector), Color.GRAY));
		for (int i=0;i<96;i+=5)
			for (int j=0;j<96;j+=5)
				if (!currentRoom.getDoor(Room.up)||i<21||i>69||j<21||j>69)
					planeList.add(new Plane(new Vector(j,i,100).plus(roomVector), new Vector(j+10,i,100).plus(roomVector), new Vector(j+10,i+10,100).plus(roomVector), new Vector(j,i+10,100).plus(roomVector), Color.GRAY));
		for (int i=0;i<96;i+=5)
			for (int j=0;j<96;j+=5)
				if (!currentRoom.getDoor(Room.south)||i>59||j<21||j>69)
					planeList.add(new Plane(new Vector(j,0,i).plus(roomVector), new Vector(j+10,0,i).plus(roomVector), new Vector(j+10,0,i+10).plus(roomVector), new Vector(j,0,i+10).plus(roomVector), roomColor));
		for (int i=0;i<96;i+=5)
			for (int j=0;j<96;j+=5)
				if (!currentRoom.getDoor(Room.north)||i>59||j<21||j>69)
					planeList.add(new Plane(new Vector(j,100,i).plus(roomVector), new Vector(j+10,100,i).plus(roomVector), new Vector(j+10,100,i+10).plus(roomVector), new Vector(j,100,i+10).plus(roomVector), roomColor));
		for (int i=0;i<96;i+=5)
			for (int j=0;j<96;j+=5)
				if (!currentRoom.getDoor(Room.west)||i>59||j<21||j>69)
					planeList.add(new Plane(new Vector(0,j,i).plus(roomVector), new Vector(0,j+10,i).plus(roomVector), new Vector(0,j+10,i+10).plus(roomVector), new Vector(0,j,i+10).plus(roomVector), roomColor));
		for (int i=0;i<96;i+=5)
			for (int j=0;j<96;j+=5)
				if (!currentRoom.getDoor(Room.east)||i>59||j<21||j>69)
					planeList.add(new Plane(new Vector(100,j,i).plus(roomVector), new Vector(100,j+10,i).plus(roomVector), new Vector(100,j+10,i+10).plus(roomVector), new Vector(100,j,i+10).plus(roomVector), roomColor));
		
		planeList.add(new Plane(new Vector(1,99,0).plus(roomVector), new Vector(2,99,0).plus(roomVector), new Vector(0,101,107).plus(roomVector), new Vector(-1,101,107).plus(roomVector), Color.BLACK));
		planeList.add(new Plane(new Vector(99,99,0).plus(roomVector), new Vector(100,99,0).plus(roomVector), new Vector(102,101,107).plus(roomVector), new Vector(101,101,107).plus(roomVector), Color.BLACK));
		planeList.add(new Plane(new Vector(0,0,0).plus(roomVector), new Vector(0,1,0).plus(roomVector), new Vector(0,1,105).plus(roomVector), new Vector(0,0,105).plus(roomVector), Color.BLACK));
		planeList.add(new Plane(new Vector(99,0,0).plus(roomVector), new Vector(99,1,0).plus(roomVector), new Vector(99,1,105).plus(roomVector), new Vector(99,0,105).plus(roomVector), Color.BLACK));

		for (int i = 0; i < 6; i++)
			if (currentRoom.getDoor(i))
				doorColorArray[i] = Color.BLACK;
			else if (0 <= i && i <= 3)
				doorColorArray[i] = roomColor;
			else
				doorColorArray[i] = Color.GRAY;

		planeList.add(new Plane(new Vector(70-40*Math.cos(doorAngleN),100+40*Math.sin(doorAngleN),0).plus(roomVector), new Vector(70,100,0).plus(roomVector),
				new Vector(70,100,60).plus(roomVector), new Vector(70-40*Math.cos(doorAngleN),100+40*Math.sin(doorAngleN),60).plus(roomVector), doorColorArray[0]));
		planeList.add(new Plane(new Vector(100,30,0).plus(roomVector), new Vector(100+40*Math.sin(doorAngleE),30+40*Math.cos(doorAngleE),0).plus(roomVector),
				new Vector(100+40*Math.sin(doorAngleE),30+40*Math.cos(doorAngleE),60).plus(roomVector), new Vector(100,30,60).plus(roomVector), doorColorArray[1]));
		planeList.add(new Plane(new Vector(30,0,0).plus(roomVector), new Vector(30+40*Math.cos(doorAngleS),-40*Math.sin(doorAngleS),0).plus(roomVector),
				new Vector(30+40*Math.cos(doorAngleS),-40*Math.sin(doorAngleS),60).plus(roomVector), new Vector(30,0,60).plus(roomVector), doorColorArray[2]));
		planeList.add(new Plane(new Vector(-40*Math.sin(doorAngleW),70-40*Math.cos(doorAngleW),0).plus(roomVector), new Vector(0,70,0).plus(roomVector),
				new Vector(0,70,60).plus(roomVector), new Vector(-40*Math.sin(doorAngleW),70-40*Math.cos(doorAngleW),60).plus(roomVector), doorColorArray[3]));
		planeList.add(new Plane(new Vector(30,30,100).plus(roomVector), new Vector(30,30+40*Math.cos(doorAngleU),100-40*Math.sin(doorAngleU)).plus(roomVector),
				new Vector(70,30+40*Math.cos(doorAngleU),100-40*Math.sin(doorAngleU)).plus(roomVector), new Vector(70,30,100).plus(roomVector), doorColorArray[4]));
		planeList.add(new Plane(new Vector(30,30,0).plus(roomVector), new Vector(30,30+40*Math.cos(doorAngleD),-40*Math.sin(doorAngleD)).plus(roomVector),
				new Vector(70,30+40*Math.cos(doorAngleD),-40*Math.sin(doorAngleD)).plus(roomVector), new Vector(70,30,0).plus(roomVector), doorColorArray[5]));
		
		if (nextRoom != null && animationTimer > 60) {
			roomVector = new Vector(100*nextRoomPos.getX(), 100*nextRoomPos.getY(), 100*nextRoomPos.getZ());
			if (animationType != 4 || animationTimer > 60) {
				for (int i=0;i<91;i+=5)
					for (int j=0;j<91;j+=5)
						if (!nextRoom.getDoor(Room.down)||i<21||i>69||j<21||j>69)
							planeList.add(new Plane(new Vector(j,i,0).plus(roomVector), new Vector(j+10,i,0).plus(roomVector), new Vector(j+10,i+10,0).plus(roomVector), new Vector(j,i+10,0).plus(roomVector), Color.GRAY));
				for (int i = 30; i < 61; i += 5)
					for (int j = 30; j < 61; j += 5)
						planeList.add(new Plane(new Vector(i,j,0).plus(roomVector), new Vector(i,j+10,0).plus(roomVector), 
								new Vector(i+10,j+10,0).plus(roomVector), new Vector(i+10,j,0).plus(roomVector), nextRoomDoorColorArray[5]));
			}
			if (animationType != 5 || animationTimer > 60) {
				for (int i=0;i<91;i+=5)
					for (int j=0;j<91;j+=5)
						if (!nextRoom.getDoor(Room.up)||i<21||i>69||j<21||j>69)
							planeList.add(new Plane(new Vector(j,i,100).plus(roomVector), new Vector(j+10,i,100).plus(roomVector), new Vector(j+10,i+10,100).plus(roomVector), new Vector(j,i+10,100).plus(roomVector), Color.GRAY));
				for (int i = 30; i < 61; i += 5)
					for (int j = 30; j < 61; j += 5)
						planeList.add(new Plane(new Vector(i,j,100).plus(roomVector), new Vector(i,j+10,100).plus(roomVector), 
								new Vector(i+10,j+10,100).plus(roomVector), new Vector(i+10,j,100).plus(roomVector), nextRoomDoorColorArray[4]));
			}
		}
	}

	public void paintComponent (Graphics g) {
		super.paintComponent(g);

		//Calculates the coordinate system of the screen-plane
		Vector a_0 = new Vector(20*Math.cos(phi)*Math.sin(theta), 20*Math.cos(phi)*Math.cos(theta), 20*Math.sin(phi));
		Vector b_0 = new Vector(20*Math.sin(theta+Math.PI/2), 20*Math.cos(theta+Math.PI/2), 0);
		Vector c_0 = a_0.cross(b_0.clone()).scale(1/a_0.magnitude());
		//		System.out.println("a_0: " + a_0);
		//		System.out.println("b_0: " + b_0);
		//		System.out.println("c_0: " + c_0);

		for (Plane plane : planeList) {
			g.setColor(plane.getColor());
			//			System.out.println("Color: " + plane.getColor().toString());
			Vector[] wallCoords = {plane.getLBCorner().clone(), plane.getRBCorner().clone(), 
					plane.getRTCorner().clone(), plane.getLTCorner().clone()};


			//If all points on the plane are behind the screen, nothing should be drawn			
			int numGoodPoints = 0;
			for (Vector pos : wallCoords)
				if (((pos.minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude() > 0)
					numGoodPoints++;
			if (numGoodPoints == 0)
				continue;

			//If only one point is good, draw a triangle;
			if (numGoodPoints == 1) {
				int goodIndex = 0;
				for (int i = 0; i < 4; i++)
					if (((wallCoords[i].minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude() > 0) {
						goodIndex = i;
						break;
					}
				Vector pos1 = wallCoords[goodIndex];
				Vector pos2, pos3;
				if (goodIndex > 0)
					pos2 = wallCoords[goodIndex-1];
				else
					pos2 = wallCoords[3];
				if (goodIndex < 3)
					pos3 = wallCoords[goodIndex+1];
				else
					pos3 = wallCoords[0];

				//A slightly altered version of the normal algorithm to project the points
//				double d_1 = ((pos1.minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
//				double d_2 = ((pos2.minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
//				double d_3 = ((pos3.minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
				double d_1 = ((pos1.minus(cameraPos)).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
				double d_2 = ((pos2.minus(cameraPos)).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
				double d_3 = ((pos3.minus(cameraPos)).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
				Vector[] alteredWallCoords = new Vector[3];
				alteredWallCoords[0] = pos1;
				alteredWallCoords[1] = pos2.plus(pos1.minus(pos2).scale((Math.abs(d_2)+0.1)/(Math.abs(d_2)+Math.abs(d_1))));
				alteredWallCoords[2] = pos3.plus(pos1.minus(pos3).scale((Math.abs(d_3)+0.1)/(Math.abs(d_3)+Math.abs(d_1))));
				wallCoords = alteredWallCoords;
				int[] x = new int[3];
				int[] y = new int[3];
				for (int i = 0; i < 3; i++) {
					Vector p_0 = wallCoords[i].minus( screenPlaneRelPos.clone().scale( screenPlaneRelPos.dot(wallCoords[i].minus(a_0))/Math.pow(screenPlaneRelPos.magnitude(),2) ) );
					double d = ((wallCoords[i].minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
					//System.out.println("d: " + d);
					if (d == -1*a_0.magnitude()) {
						d = -1*a_0.magnitude()+0.01;
					}
					x[i] = (int) (8*((p_0.minus(cameraPos.plus(a_0))).dot(b_0))/b_0.magnitude() * screenPlaneRelPos.magnitude() / (d + screenPlaneRelPos.magnitude())) + 400;
					y[i] = (int) (6*((p_0.minus(cameraPos.plus(a_0))).dot(c_0)/c_0.magnitude()) * screenPlaneRelPos.magnitude() / (d + screenPlaneRelPos.magnitude())) + 300;
				}
				g.fillPolygon(x, y, 3);
				continue;
			}

			//For pairs of connected points where one is behind the camera,
			//cut it off so that the line segment is entirely in front of the camera
			Vector[] alteredWallCoords = wallCoords.clone();
			for (int i = 0; i < 4; i++) {
				Vector pos1 = wallCoords[i];
				Vector pos2;
				if (i < 3)
					pos2 = wallCoords[i+1];
				else
					pos2 = wallCoords[0];

//				double d_1 = ((pos1.minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
//				double d_2 = ((pos2.minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
				double d_1 = ((pos1.minus(cameraPos)).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
				double d_2 = ((pos2.minus(cameraPos)).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
				if (d_1 < 0 && !(d_2 < 0)) {
					alteredWallCoords[i] = pos1.plus(pos2.minus(pos1).scale((Math.abs(d_1)+0.1)/(Math.abs(d_1)+Math.abs(d_2))));
					//System.out.println("d_1: " + pos1 + " to " + alteredWallCoords[i] + " " + i);
				} else if (d_2 < 0 && !(d_1 < 0)) {
					if (i < 3) {
						alteredWallCoords[i+1] = pos2.plus(pos1.minus(pos2).scale((Math.abs(d_2)+0.1)/(Math.abs(d_2)+Math.abs(d_1))));
						//System.out.println("d_2: " + pos2 + " to " + alteredWallCoords[i+1] + " " + (i+1));
					} else {
						alteredWallCoords[0] = pos2.plus(pos1.minus(pos2).scale((Math.abs(d_2)+0.1)/(Math.abs(d_2)+Math.abs(d_1))));
						//System.out.println("d_2: " + pos2 + " to " + alteredWallCoords[0] + " 0");
					}
				}
			}
			wallCoords = alteredWallCoords;


			//Find the intersection of the line from the camera to each point using similar triangles,
			//then project that onto the planar axes to get the screen coordinates
			int[] x = new int[4];
			int[] y = new int[4];
			for (int i = 0; i < 4; i++) {
				Vector p_0 = wallCoords[i].minus( screenPlaneRelPos.clone().scale( screenPlaneRelPos.dot(wallCoords[i].minus(a_0))/Math.pow(screenPlaneRelPos.magnitude(),2) ) );
				double d = ((wallCoords[i].minus(cameraPos.plus(screenPlaneRelPos))).dot(screenPlaneRelPos))/screenPlaneRelPos.magnitude();
				//System.out.println("d: " + d);
				if (d == -1*a_0.magnitude()) {
					d = -1*a_0.magnitude()+0.01;
				}
				x[i] = (int) (8*((p_0.minus(cameraPos.plus(a_0))).dot(b_0))/b_0.magnitude() * screenPlaneRelPos.magnitude() / (d + screenPlaneRelPos.magnitude())) + 400;
				y[i] = (int) (6*((p_0.minus(cameraPos.plus(a_0))).dot(c_0)/c_0.magnitude()) * screenPlaneRelPos.magnitude() / (d + screenPlaneRelPos.magnitude())) + 300;
			}


			//			for (int i = 0; i < 4; i++)
			//				System.out.println(wallCoords[i] + " to (" + x[i] + ", " + y[i] + ") ");
			g.fillPolygon(x, y, 4);
		}
	}
}