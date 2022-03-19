package serviceTests;

import connectFour.entity.Comment;
import connectFour.service.CommentService;
import connectFour.service.CommentServiceJDBC;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class CommentServiceTests {
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testAddComment(){
        commentService.reset();
        Comment commentary = new Comment("ven", "connectfour", "100", new Date());
        commentService.addComment(commentary);
        assertEquals(1, commentService.getComments("connectfour").size());
    }

    @Test
    public void testReset(){
        commentService.reset();
        assertEquals(0, commentService.getComments("connectfour").size());
    }

    @Test
    public void testGetAllComments(){
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("Jaro", "connectfour", "120", date));
        commentService.addComment(new Comment("Katka", "connectfour", "150", date));
        commentService.addComment(new Comment("Zuzka", "ctf", "180", date));
        commentService.addComment(new Comment("Jaro", "connectfour", "100", date));

        var commentars = commentService.getComments("connectfour");

        assertEquals(3, commentars.size());

        assertEquals("connectfour", commentars.get(0).getGame());
        assertEquals("Jaro", commentars.get(0).getPlayer());
        assertEquals("120", commentars.get(0).getComment());
        assertEquals(date, commentars.get(0).getCommentedOn());

        assertEquals("connectfour", commentars.get(1).getGame());
        assertEquals("Katka", commentars.get(1).getPlayer());
        assertEquals("150", commentars.get(1).getComment());
        assertEquals(date, commentars.get(1).getCommentedOn());

        assertEquals("connectfour", commentars.get(2).getGame());
        assertEquals("Jaro", commentars.get(2).getPlayer());
        assertEquals("100", commentars.get(2).getComment());
        assertEquals(date, commentars.get(2).getCommentedOn());

    }
}
