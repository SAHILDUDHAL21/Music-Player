import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MusicPlayerGUI extends JFrame implements ActionListener
{
        JTextField fpf;
        JLabel im;
        JButton play,pause,choose,loop;
        boolean ispause,islooping;
        JFileChooser fchose;
        Clip clip;
        ImageIcon img;
        Image img1;


        MusicPlayerGUI()
        {
                super("Music Player");
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                this.setLayout(new FlowLayout());
                this.setSize(370,500);
                this.setResizable(false);
                this.setLocationRelativeTo(null);

                fpf=new JTextField(29);
                play=new JButton("Play");
                pause=new JButton("Pause");
                choose=new JButton("Choose File");
                loop=new JButton("Loop");
                islooping=false;
                ispause=false;

                img=new ImageIcon("src/pngegg.png");
                img1= img.getImage().getScaledInstance(370,370,Image.SCALE_SMOOTH);
                im=new JLabel(new ImageIcon(img1));
                play.addActionListener(this);
                pause.addActionListener(this);
                choose.addActionListener(this);
                loop.addActionListener(this);

                add(im);
                add(fpf); add(choose); add(play); add(pause); add(loop);
                fchose=new JFileChooser(".");
                fchose.setFileFilter(new FileNameExtensionFilter("WAV Files","wav"));
                this.setVisible(true);

        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
                if(e.getSource()== play)
                {
                        playMusic();
                }
                else if (e.getSource()== pause)
                {
                      pauseMusic();
                }
                else if (e.getSource()==choose)
                {
                      chooseFile();
                }
                else if (e.getSource()==loop)
                {
                        loopOn();
                }
        }

        private void loopOn()
        {
                islooping=!islooping;
                if (islooping)
                {
                        loop.setText("Stop Loop");

                        if (clip.isRunning())
                        {
                                clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }

                }
                else
                {
                        loop.setText("Loop");
                        if (clip.isRunning())
                        {
                                clip.loop(0);
                        }
                }
        }

        private void chooseFile()
        {
                fchose.setCurrentDirectory(new File("."));
                int result = fchose.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                        File SelectFile=fchose.getSelectedFile();
                        fpf.setText(SelectFile.getAbsolutePath());
                }
        }

        private void pauseMusic()
        {
                if(clip!=null && clip.isRunning())
                {
                        clip.stop();
                        ispause=true;
                        pause.setText("Resume");
                }
                else if (clip!=null && ispause)
                {
                        clip.start();
                        if (islooping)
                        {
                                clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }

                        ispause=false;
                        pause.setText("Pause");
                }
        }

        private void playMusic()
        {
                if(clip!=null && clip.isRunning())
                {
                        clip.stop();
                }
                try
                {
                        File file =new File(fpf.getText());
                        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);

                        clip=AudioSystem.getClip();
                        clip.open(audioIn);

                        if(islooping)
                        {
                                clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }

                        clip.start();
                }
                catch (Exception e)
                {
                        System.out.println(e);
                }
        }
}
