package demo; import gov.nasa.jpf.symbc.Debug;public class CharToBytes {
public static byte[] charToBytes( char car )
    {
        if ( car <= 0x007F )
        {
            byte[] bytes = new byte[1];

            // Single byte char
            bytes[0] = ( byte ) car;
            
            return bytes;
        }
        else if ( car <= 0x07FF )
        {
            byte[] bytes = new byte[2];

            // two bytes char
            bytes[0] = ( byte ) ( 0x00C0 + ( ( car & 0x07C0 ) >> 6 ) );
            bytes[1] = ( byte ) ( 0x0080 + ( car & 0x3F ) );
            
            return bytes;
        }
        else
        {
            byte[] bytes = new byte[3];

            // Three bytes char
            bytes[0] = ( byte ) ( 0x00E0 + ( ( car & 0xF000 ) >> 12 ) );
            bytes[1] = ( byte ) ( 0x0080 + ( ( car & 0x0FC0 ) >> 6 ) );
            bytes[2] = ( byte ) ( 0x0080 + ( car & 0x3F ) );
            
            return bytes;
        }
    }
public static void main(String[] args){
charToBytes();
}}