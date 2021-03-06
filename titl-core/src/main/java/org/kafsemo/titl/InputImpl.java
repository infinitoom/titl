/*
 *  titl - Tools for iTunes Libraries
 *  Copyright (C) 2008-2011 Joseph Walton
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.kafsemo.titl;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputImpl implements Input
{
    private final InputStream in;
    private final DataInput di;
    private long position = 0;

    public InputImpl(InputStream in)
    {
        this.in = new CountingInputStream(in);
        this.di = new DataInputStream(this.in);
    }

    public long getPosition()
    {
        return position;
    }

    private class CountingInputStream extends FilterInputStream
    {
        public CountingInputStream(InputStream in)
        {
            super(in);
        }

        @Override
        public int read() throws IOException
        {
            int r = super.read();
            if (r >= 0) {
                position++;
            }
            return r;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException
        {
            int count = super.read(b, off, len);
            if (count >= 0) {
                position += count;
            }
            return count;
        }

        @Override
        public long skip(long n) throws IOException
        {
            long count = super.skip(n);
            if (count >= 0) {
                position += count;
            }
            return count;
        }
    }

    @Override
    public int readUnsignedByte() throws IOException
    {
        return di.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException
    {
        return di.readShort();
    }

    @Override
    public int readInt() throws IOException
    {
        return di.readInt();
    }

    @Override
    public void readFully(byte[] b) throws IOException
    {
        di.readFully(b);
    }

    @Override
    public int skipBytes(int n) throws IOException
    {
        return di.skipBytes(n);
    }
}
